(ns ci-seer.pr-seers.github
  (:require [ci-seer.pr-seers.core :as core]
            [tentacles.pulls :as pulls]
            [schema.core :as schema]
            [clj-time.coerce :as ctime]))

(schema/defn raw-pr-data->pr-seer-data :- core/PrData
  "Parses the raw response from Github's pulls API call into the structure
  described by `PrData`."
  [data]
  ;; TODO: examine the api limits and log an error if no queries are left
  (let [prs (rest data)]
    (if (not (empty? prs))
      {:oldest-pr (ctime/from-string (:created_at (first prs)))
       :num-prs   (count prs)}
      {:oldest-pr nil
       :num-prs   0})))

(defn get-auth-key
  "Examines the given authentication map and returns the auth data needed by
  the tentacles API. Right now only an OAuth token is supported in the form of
  `{:oauth-token \"TOKEN\"}`. If this map form is not found then nil is
  returned."
  [auth-info]
  (let [{:keys [oauth-token]} auth-info]
    (cond
      oauth-token {:oauth-token (:oauth-token auth-info)}
      :else       nil)))

(def pr-seer
  (reify core/PrSeer

    (supported-system
      [_]
      :github)

    (get-pr-data
      [_ user repo auth-info]
      (let [auth-key (get-auth-key auth-info)
            opts     (merge auth-key {:sort :created})]
        (-> (pulls/pulls user repo opts)
            raw-pr-data->pr-seer-data)))))
