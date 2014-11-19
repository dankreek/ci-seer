(ns ci-seer.pr-seers.github
  (:import (clojure.lang Atom)
           (java.net URL))
  (:require [ci-seer.pr-seers.core :as core]
            [tentacles.pulls :as pulls]
            [schema.core :as schema]
            [clj-time.coerce :as ctime]
            [clojure.string :as str]
            [slingshot.slingshot :refer [throw+]]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Schemas

(def GithubRepoList
  ;; A list of repos in the form of "user/repository"
  [schema/Str])

(def GithubRepoListWithKey
  {;; A global OAuth key for each github API call
   :oauth-token schema/Str
   ;; A list of repositories
   :repos       GithubRepoList})

(def GithubConfig
  (schema/either
    GithubRepoList
    GithubRepoListWithKey))

(def GithubRepoContext
  "A map which identifies a Github repostory and possibly an OAuth key for
  API requests."
  {:user                schema/Str
   :repo                schema/Str
   (schema/optional-key
     :oauth-token)      schema/Str})

(def GithubPrSeerContext
  "The Github PR-Seer context, which is simply a list of repository maps."
  [GithubRepoContext])

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Utils

(schema/defn raw-pr-data->pr-seer-data
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

(defn parse-repo-str
  "Parse a repository identifier in the form of `user/repo` into a map."
  [repo-str]
  (let [[user repo] (str/split repo-str #"/")]
    {:user user
     :repo repo}))

(schema/defn parse-repos-list
  [repos :- GithubRepoList]
  (map parse-repo-str repos))

(schema/defn github-config->context :- GithubPrSeerContext
  [config]
  (cond
    (nil? (schema/check GithubRepoList config))
    (parse-repos-list config)

    (nil? (schema/check GithubRepoListWithKey config))
    (let [{:keys [oauth-token repos]} config]
      (map #(merge % {:oauth-token oauth-token})
           (parse-repos-list repos)))

    :else
    (throw+ {:type    ::invalid-config
             :message "An invalid configuration was provided."
             :config  config})))

(schema/defn get-prs-for-repo :- core/PrData
  [{:keys [user repo]} :- GithubRepoContext]
  (let [opts {:sort :created}]
    (-> (pulls/pulls user repo opts)
        raw-pr-data->pr-seer-data
        (merge {:repo repo
                :repo-url (URL. (str "https://github.com/" user "/" repo))}))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; PR-Seer implementation

(def pr-seer
  (reify core/PrSeer

    (supported-system [_] :github)

    (config->context
      [_ config]
      (github-config->context config))

    (get-pr-data
      [_ context]
      (map get-prs-for-repo context))))
