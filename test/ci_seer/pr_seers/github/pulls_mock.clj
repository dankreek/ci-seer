(ns ci-seer.pr-seers.github.pulls-mock)

(def pulls-response
  '({:X-RateLimit-Remaining "57", :X-RateLimit-Limit "60"}
   {:html_url         "https://github.com/puppetlabs/puppet-server/pull/225",
    :merge_commit_sha "ea82e1d5fc38ed7e0a899c40973c800e5dfc08ad",
    :patch_url
                      "https://github.com/puppetlabs/puppet-server/pull/225.patch",
    :closed_at        nil,
    :review_comment_url
                      "https://api.github.com/repos/puppetlabs/puppet-server/pulls/comments/{number}",
    :number           225,
    :milestone        nil,
    :merged_at        nil,
    :statuses_url
                      "https://api.github.com/repos/puppetlabs/puppet-server/statuses/05f5297bffb6d94310b72a791a81fd3ebc81c05e",
    :state            "open",
    :issue_url
                      "https://api.github.com/repos/puppetlabs/puppet-server/issues/225",
    :title
                      "(SERVER-39) Allow a certificate to be passed into a request via X header",
    :commits_url
                      "https://api.github.com/repos/puppetlabs/puppet-server/pulls/225/commits",
    :updated_at       "2014-10-15T19:02:14Z",
    :head
                      {:label
                            "camlow325:feature/master/SERVER-39-allow-certificate-via-x-header",
                       :ref "feature/master/SERVER-39-allow-certificate-via-x-header",
                       :sha "05f5297bffb6d94310b72a791a81fd3ebc81c05e",
                       :user
                            {:html_url    "https://github.com/camlow325",
                             :gravatar_id "",
                             :followers_url
                                          "https://api.github.com/users/camlow325/followers",
                             :subscriptions_url
                                          "https://api.github.com/users/camlow325/subscriptions",
                             :site_admin  false,
                             :following_url
                                          "https://api.github.com/users/camlow325/following{/other_user}",
                             :type        "User",
                             :received_events_url
                                                "https://api.github.com/users/camlow325/received_events",
                             :login             "camlow325",
                             :organizations_url "https://api.github.com/users/camlow325/orgs",
                             :id                5652737,
                             :events_url
                                                "https://api.github.com/users/camlow325/events{/privacy}",
                             :url               "https://api.github.com/users/camlow325",
                             :repos_url         "https://api.github.com/users/camlow325/repos",
                             :starred_url
                                                "https://api.github.com/users/camlow325/starred{/owner}{/repo}",
                             :gists_url
                                                "https://api.github.com/users/camlow325/gists{/gist_id}",
                             :avatar_url
                                                "https://avatars.githubusercontent.com/u/5652737?v=2"},
                       :repo
                            {:html_url          "https://github.com/camlow325/puppet-server",
                             :description       "Server automation framework and application",
                             :open_issues_count 0,
                             :watchers          0,
                             :ssh_url           "git@github.com:camlow325/puppet-server.git",
                             :hooks_url
                                                "https://api.github.com/repos/camlow325/puppet-server/hooks",
                             :archive_url
                                                "https://api.github.com/repos/camlow325/puppet-server/{archive_format}{/ref}",
                             :keys_url
                                                "https://api.github.com/repos/camlow325/puppet-server/keys{/key_id}",
                             :forks_count       0,
                             :languages_url
                                                "https://api.github.com/repos/camlow325/puppet-server/languages",
                             :git_url           "git://github.com/camlow325/puppet-server.git",
                             :issue_comment_url
                                                "https://api.github.com/repos/camlow325/puppet-server/issues/comments/{number}",
                             :git_refs_url
                                                "https://api.github.com/repos/camlow325/puppet-server/git/refs{/sha}",
                             :clone_url         "https://github.com/camlow325/puppet-server.git",
                             :contents_url
                                                "https://api.github.com/repos/camlow325/puppet-server/contents/{+path}",
                             :has_downloads     true,
                             :teams_url
                                                "https://api.github.com/repos/camlow325/puppet-server/teams",
                             :has_issues        false,
                             :issue_events_url
                                                "https://api.github.com/repos/camlow325/puppet-server/issues/events{/number}",
                             :private           false,
                             :watchers_count    0,
                             :collaborators_url
                                                "https://api.github.com/repos/camlow325/puppet-server/collaborators{/collaborator}",
                             :homepage          "https://tickets.puppetlabs.com/browse/SERVER",
                             :git_commits_url
                                                "https://api.github.com/repos/camlow325/puppet-server/git/commits{/sha}",
                             :name              "puppet-server",
                             :releases_url
                                                "https://api.github.com/repos/camlow325/puppet-server/releases{/id}",
                             :milestones_url
                                                "https://api.github.com/repos/camlow325/puppet-server/milestones{/number}",
                             :svn_url           "https://github.com/camlow325/puppet-server",
                             :merges_url
                                                "https://api.github.com/repos/camlow325/puppet-server/merges",
                             :compare_url
                                                "https://api.github.com/repos/camlow325/puppet-server/compare/{base}...{head}",
                             :stargazers_count  0,
                             :tags_url
                                                "https://api.github.com/repos/camlow325/puppet-server/tags",
                             :statuses_url
                                                "https://api.github.com/repos/camlow325/puppet-server/statuses/{sha}",
                             :notifications_url
                                                "https://api.github.com/repos/camlow325/puppet-server/notifications{?since,all,participating}",
                             :open_issues       0,
                             :has_wiki          true,
                             :size              994,
                             :assignees_url
                                                "https://api.github.com/repos/camlow325/puppet-server/assignees{/user}",
                             :commits_url
                                                "https://api.github.com/repos/camlow325/puppet-server/commits{/sha}",
                             :labels_url
                                                "https://api.github.com/repos/camlow325/puppet-server/labels{/name}",
                             :forks_url
                                                "https://api.github.com/repos/camlow325/puppet-server/forks",
                             :contributors_url
                                                "https://api.github.com/repos/camlow325/puppet-server/contributors",
                             :updated_at        "2014-10-02T00:32:48Z",
                             :pulls_url
                                                "https://api.github.com/repos/camlow325/puppet-server/pulls{/number}",
                             :has_pages         false,
                             :default_branch    "master",
                             :language          "Clojure",
                             :comments_url
                                                "https://api.github.com/repos/camlow325/puppet-server/comments{/number}",
                             :id                24697001,
                             :stargazers_url
                                                "https://api.github.com/repos/camlow325/puppet-server/stargazers",
                             :issues_url
                                                "https://api.github.com/repos/camlow325/puppet-server/issues{/number}",
                             :trees_url
                                                "https://api.github.com/repos/camlow325/puppet-server/git/trees{/sha}",
                             :events_url
                                                "https://api.github.com/repos/camlow325/puppet-server/events",
                             :branches_url
                                                "https://api.github.com/repos/camlow325/puppet-server/branches{/branch}",
                             :url               "https://api.github.com/repos/camlow325/puppet-server",
                             :downloads_url
                                                "https://api.github.com/repos/camlow325/puppet-server/downloads",
                             :forks             0,
                             :subscribers_url
                                                "https://api.github.com/repos/camlow325/puppet-server/subscribers",
                             :full_name         "camlow325/puppet-server",
                             :blobs_url
                                                "https://api.github.com/repos/camlow325/puppet-server/git/blobs{/sha}",
                             :subscription_url
                                                "https://api.github.com/repos/camlow325/puppet-server/subscription",
                             :fork              true,
                             :pushed_at         "2014-10-15T22:31:03Z",
                             :owner
                                                {:html_url    "https://github.com/camlow325",
                                                 :gravatar_id "",
                                                 :followers_url
                                                              "https://api.github.com/users/camlow325/followers",
                                                 :subscriptions_url
                                                              "https://api.github.com/users/camlow325/subscriptions",
                                                 :site_admin  false,
                                                 :following_url
                                                              "https://api.github.com/users/camlow325/following{/other_user}",
                                                 :type        "User",
                                                 :received_events_url
                                                              "https://api.github.com/users/camlow325/received_events",
                                                 :login       "camlow325",
                                                 :organizations_url
                                                              "https://api.github.com/users/camlow325/orgs",
                                                 :id          5652737,
                                                 :events_url
                                                              "https://api.github.com/users/camlow325/events{/privacy}",
                                                 :url         "https://api.github.com/users/camlow325",
                                                 :repos_url   "https://api.github.com/users/camlow325/repos",
                                                 :starred_url
                                                              "https://api.github.com/users/camlow325/starred{/owner}{/repo}",
                                                 :gists_url
                                                              "https://api.github.com/users/camlow325/gists{/gist_id}",
                                                 :avatar_url
                                                              "https://avatars.githubusercontent.com/u/5652737?v=2"},
                             :git_tags_url
                                                "https://api.github.com/repos/camlow325/puppet-server/git/tags{/sha}",
                             :created_at        "2014-10-01T21:59:27Z",
                             :mirror_url        nil}},
    :diff_url
                      "https://github.com/puppetlabs/puppet-server/pull/225.diff",
    :comments_url
                      "https://api.github.com/repos/puppetlabs/puppet-server/issues/225/comments",
    :locked           false,
    :id               22806594,
    :url
                      "https://api.github.com/repos/puppetlabs/puppet-server/pulls/225",
    :base
                      {:label "puppetlabs:master",
                       :ref   "master",
                       :sha   "d99e65107a1f947758ac4d1bd81de018afa9b3dc",
                       :user
                              {:html_url    "https://github.com/puppetlabs",
                               :gravatar_id "",
                               :followers_url
                                            "https://api.github.com/users/puppetlabs/followers",
                               :subscriptions_url
                                            "https://api.github.com/users/puppetlabs/subscriptions",
                               :site_admin  false,
                               :following_url
                                            "https://api.github.com/users/puppetlabs/following{/other_user}",
                               :type        "Organization",
                               :received_events_url
                                            "https://api.github.com/users/puppetlabs/received_events",
                               :login       "puppetlabs",
                               :organizations_url
                                            "https://api.github.com/users/puppetlabs/orgs",
                               :id          234268,
                               :events_url
                                            "https://api.github.com/users/puppetlabs/events{/privacy}",
                               :url         "https://api.github.com/users/puppetlabs",
                               :repos_url   "https://api.github.com/users/puppetlabs/repos",
                               :starred_url
                                            "https://api.github.com/users/puppetlabs/starred{/owner}{/repo}",
                               :gists_url
                                            "https://api.github.com/users/puppetlabs/gists{/gist_id}",
                               :avatar_url
                                            "https://avatars.githubusercontent.com/u/234268?v=2"},
                       :repo
                              {:html_url          "https://github.com/puppetlabs/puppet-server",
                               :description       "Server automation framework and application",
                               :open_issues_count 2,
                               :watchers          62,
                               :ssh_url           "git@github.com:puppetlabs/puppet-server.git",
                               :hooks_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/hooks",
                               :archive_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/{archive_format}{/ref}",
                               :keys_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/keys{/key_id}",
                               :forks_count       19,
                               :languages_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/languages",
                               :git_url           "git://github.com/puppetlabs/puppet-server.git",
                               :issue_comment_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/issues/comments/{number}",
                               :git_refs_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/git/refs{/sha}",
                               :clone_url         "https://github.com/puppetlabs/puppet-server.git",
                               :contents_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/contents/{+path}",
                               :has_downloads     true,
                               :teams_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/teams",
                               :has_issues        false,
                               :issue_events_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/issues/events{/number}",
                               :private           false,
                               :watchers_count    62,
                               :collaborators_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/collaborators{/collaborator}",
                               :homepage          "https://tickets.puppetlabs.com/browse/SERVER",
                               :git_commits_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/git/commits{/sha}",
                               :name              "puppet-server",
                               :releases_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/releases{/id}",
                               :milestones_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/milestones{/number}",
                               :svn_url           "https://github.com/puppetlabs/puppet-server",
                               :merges_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/merges",
                               :compare_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/compare/{base}...{head}",
                               :stargazers_count  62,
                               :tags_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/tags",
                               :statuses_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/statuses/{sha}",
                               :notifications_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/notifications{?since,all,participating}",
                               :open_issues       2,
                               :has_wiki          true,
                               :size              5300,
                               :assignees_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/assignees{/user}",
                               :commits_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/commits{/sha}",
                               :labels_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/labels{/name}",
                               :forks_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/forks",
                               :contributors_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/contributors",
                               :updated_at        "2014-10-12T16:53:41Z",
                               :pulls_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/pulls{/number}",
                               :has_pages         false,
                               :default_branch    "master",
                               :language          "Clojure",
                               :comments_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/comments{/number}",
                               :id                19546488,
                               :stargazers_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/stargazers",
                               :issues_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/issues{/number}",
                               :trees_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/git/trees{/sha}",
                               :events_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/events",
                               :branches_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/branches{/branch}",
                               :url               "https://api.github.com/repos/puppetlabs/puppet-server",
                               :downloads_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/downloads",
                               :forks             19,
                               :subscribers_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/subscribers",
                               :full_name         "puppetlabs/puppet-server",
                               :blobs_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/git/blobs{/sha}",
                               :subscription_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/subscription",
                               :fork              false,
                               :pushed_at         "2014-10-15T21:30:13Z",
                               :owner
                                                  {:html_url    "https://github.com/puppetlabs",
                                                   :gravatar_id "",
                                                   :followers_url
                                                                "https://api.github.com/users/puppetlabs/followers",
                                                   :subscriptions_url
                                                                "https://api.github.com/users/puppetlabs/subscriptions",
                                                   :site_admin  false,
                                                   :following_url
                                                                "https://api.github.com/users/puppetlabs/following{/other_user}",
                                                   :type        "Organization",
                                                   :received_events_url
                                                                "https://api.github.com/users/puppetlabs/received_events",
                                                   :login       "puppetlabs",
                                                   :organizations_url
                                                                "https://api.github.com/users/puppetlabs/orgs",
                                                   :id          234268,
                                                   :events_url
                                                                "https://api.github.com/users/puppetlabs/events{/privacy}",
                                                   :url         "https://api.github.com/users/puppetlabs",
                                                   :repos_url   "https://api.github.com/users/puppetlabs/repos",
                                                   :starred_url
                                                                "https://api.github.com/users/puppetlabs/starred{/owner}{/repo}",
                                                   :gists_url
                                                                "https://api.github.com/users/puppetlabs/gists{/gist_id}",
                                                   :avatar_url
                                                                "https://avatars.githubusercontent.com/u/234268?v=2"},
                               :git_tags_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/git/tags{/sha}",
                               :created_at        "2014-05-07T19:00:24Z",
                               :mirror_url        nil}},
    :_links
                      {:self
                        {:href
                          "https://api.github.com/repos/puppetlabs/puppet-server/pulls/225"},
                       :html
                        {:href "https://github.com/puppetlabs/puppet-server/pull/225"},
                       :issue
                        {:href
                          "https://api.github.com/repos/puppetlabs/puppet-server/issues/225"},
                       :comments
                        {:href
                          "https://api.github.com/repos/puppetlabs/puppet-server/issues/225/comments"},
                       :review_comments
                        {:href
                          "https://api.github.com/repos/puppetlabs/puppet-server/pulls/225/comments"},
                       :review_comment
                        {:href
                          "https://api.github.com/repos/puppetlabs/puppet-server/pulls/comments/{number}"},
                       :commits
                        {:href
                          "https://api.github.com/repos/puppetlabs/puppet-server/pulls/225/commits"},
                       :statuses
                        {:href
                          "https://api.github.com/repos/puppetlabs/puppet-server/statuses/05f5297bffb6d94310b72a791a81fd3ebc81c05e"}},
    :body
                      "This commit adds the ability for a certificate to be supplied in a web\r\nrequest as the payload of a custom HTTP header, X-Client-Cert.  The\r\nheader will only be parsed for a value if the `allow-header-cert-info`\r\nmaster setting is set to true.  When the certificate is retrieved, it\r\nwill be supplied as the client certificate to the Ruby request handler\r\nstack, as a direct replacement for a certificate object that would be\r\ntypically be provided over SSL.",
    :user
                      {:html_url      "https://github.com/camlow325",
                       :gravatar_id   "",
                       :followers_url "https://api.github.com/users/camlow325/followers",
                       :subscriptions_url
                                      "https://api.github.com/users/camlow325/subscriptions",
                       :site_admin    false,
                       :following_url
                                      "https://api.github.com/users/camlow325/following{/other_user}",
                       :type          "User",
                       :received_events_url
                                          "https://api.github.com/users/camlow325/received_events",
                       :login             "camlow325",
                       :organizations_url "https://api.github.com/users/camlow325/orgs",
                       :id                5652737,
                       :events_url
                                          "https://api.github.com/users/camlow325/events{/privacy}",
                       :url               "https://api.github.com/users/camlow325",
                       :repos_url         "https://api.github.com/users/camlow325/repos",
                       :starred_url
                                          "https://api.github.com/users/camlow325/starred{/owner}{/repo}",
                       :gists_url
                                          "https://api.github.com/users/camlow325/gists{/gist_id}",
                       :avatar_url
                                          "https://avatars.githubusercontent.com/u/5652737?v=2"},
    :review_comments_url
                      "https://api.github.com/repos/puppetlabs/puppet-server/pulls/225/comments",
    :assignee         nil,
    :created_at       "2014-10-15T19:02:14Z"}
   {:html_url         "https://github.com/puppetlabs/puppet-server/pull/224",
    :merge_commit_sha "ef0513f63171224afa13979d39b285bb928ca021",
    :patch_url
                      "https://github.com/puppetlabs/puppet-server/pull/224.patch",
    :closed_at        nil,
    :review_comment_url
                      "https://api.github.com/repos/puppetlabs/puppet-server/pulls/comments/{number}",
    :number           224,
    :milestone        nil,
    :merged_at        nil,
    :statuses_url
                      "https://api.github.com/repos/puppetlabs/puppet-server/statuses/21f02f8e20320a08e2d3f6520df01978a5f5cda3",
    :state            "open",
    :issue_url
                      "https://api.github.com/repos/puppetlabs/puppet-server/issues/224",
    :title            "(SERVER-61) improvements to the ExecutionStub",
    :commits_url
                      "https://api.github.com/repos/puppetlabs/puppet-server/pulls/224/commits",
    :updated_at       "2014-10-15T22:22:23Z",
    :head
                      {:label "KevinCorcoran:SERVER-61/ugh",
                       :ref   "SERVER-61/ugh",
                       :sha   "21f02f8e20320a08e2d3f6520df01978a5f5cda3",
                       :user
                              {:html_url    "https://github.com/KevinCorcoran",
                               :gravatar_id "",
                               :followers_url
                                            "https://api.github.com/users/KevinCorcoran/followers",
                               :subscriptions_url
                                            "https://api.github.com/users/KevinCorcoran/subscriptions",
                               :site_admin  false,
                               :following_url
                                            "https://api.github.com/users/KevinCorcoran/following{/other_user}",
                               :type        "User",
                               :received_events_url
                                            "https://api.github.com/users/KevinCorcoran/received_events",
                               :login       "KevinCorcoran",
                               :organizations_url
                                            "https://api.github.com/users/KevinCorcoran/orgs",
                               :id          2945462,
                               :events_url
                                            "https://api.github.com/users/KevinCorcoran/events{/privacy}",
                               :url         "https://api.github.com/users/KevinCorcoran",
                               :repos_url   "https://api.github.com/users/KevinCorcoran/repos",
                               :starred_url
                                            "https://api.github.com/users/KevinCorcoran/starred{/owner}{/repo}",
                               :gists_url
                                            "https://api.github.com/users/KevinCorcoran/gists{/gist_id}",
                               :avatar_url
                                            "https://avatars.githubusercontent.com/u/2945462?v=2"},
                       :repo
                              {:html_url          "https://github.com/KevinCorcoran/puppet-server",
                               :description       "Server automation framework and application",
                               :open_issues_count 0,
                               :watchers          0,
                               :ssh_url           "git@github.com:KevinCorcoran/puppet-server.git",
                               :hooks_url
                                                  "https://api.github.com/repos/KevinCorcoran/puppet-server/hooks",
                               :archive_url
                                                  "https://api.github.com/repos/KevinCorcoran/puppet-server/{archive_format}{/ref}",
                               :keys_url
                                                  "https://api.github.com/repos/KevinCorcoran/puppet-server/keys{/key_id}",
                               :forks_count       0,
                               :languages_url
                                                  "https://api.github.com/repos/KevinCorcoran/puppet-server/languages",
                               :git_url           "git://github.com/KevinCorcoran/puppet-server.git",
                               :issue_comment_url
                                                  "https://api.github.com/repos/KevinCorcoran/puppet-server/issues/comments/{number}",
                               :git_refs_url
                                                  "https://api.github.com/repos/KevinCorcoran/puppet-server/git/refs{/sha}",
                               :clone_url         "https://github.com/KevinCorcoran/puppet-server.git",
                               :contents_url
                                                  "https://api.github.com/repos/KevinCorcoran/puppet-server/contents/{+path}",
                               :has_downloads     true,
                               :teams_url
                                                  "https://api.github.com/repos/KevinCorcoran/puppet-server/teams",
                               :has_issues        false,
                               :issue_events_url
                                                  "https://api.github.com/repos/KevinCorcoran/puppet-server/issues/events{/number}",
                               :private           false,
                               :watchers_count    0,
                               :collaborators_url
                                                  "https://api.github.com/repos/KevinCorcoran/puppet-server/collaborators{/collaborator}",
                               :homepage          "https://tickets.puppetlabs.com/browse/SERVER",
                               :git_commits_url
                                                  "https://api.github.com/repos/KevinCorcoran/puppet-server/git/commits{/sha}",
                               :name              "puppet-server",
                               :releases_url
                                                  "https://api.github.com/repos/KevinCorcoran/puppet-server/releases{/id}",
                               :milestones_url
                                                  "https://api.github.com/repos/KevinCorcoran/puppet-server/milestones{/number}",
                               :svn_url           "https://github.com/KevinCorcoran/puppet-server",
                               :merges_url
                                                  "https://api.github.com/repos/KevinCorcoran/puppet-server/merges",
                               :compare_url
                                                  "https://api.github.com/repos/KevinCorcoran/puppet-server/compare/{base}...{head}",
                               :stargazers_count  0,
                               :tags_url
                                                  "https://api.github.com/repos/KevinCorcoran/puppet-server/tags",
                               :statuses_url
                                                  "https://api.github.com/repos/KevinCorcoran/puppet-server/statuses/{sha}",
                               :notifications_url
                                                  "https://api.github.com/repos/KevinCorcoran/puppet-server/notifications{?since,all,participating}",
                               :open_issues       0,
                               :has_wiki          true,
                               :size              1121,
                               :assignees_url
                                                  "https://api.github.com/repos/KevinCorcoran/puppet-server/assignees{/user}",
                               :commits_url
                                                  "https://api.github.com/repos/KevinCorcoran/puppet-server/commits{/sha}",
                               :labels_url
                                                  "https://api.github.com/repos/KevinCorcoran/puppet-server/labels{/name}",
                               :forks_url
                                                  "https://api.github.com/repos/KevinCorcoran/puppet-server/forks",
                               :contributors_url
                                                  "https://api.github.com/repos/KevinCorcoran/puppet-server/contributors",
                               :updated_at        "2014-10-07T17:42:48Z",
                               :pulls_url
                                                  "https://api.github.com/repos/KevinCorcoran/puppet-server/pulls{/number}",
                               :has_pages         false,
                               :default_branch    "master",
                               :language          nil,
                               :comments_url
                                                  "https://api.github.com/repos/KevinCorcoran/puppet-server/comments{/number}",
                               :id                24964041,
                               :stargazers_url
                                                  "https://api.github.com/repos/KevinCorcoran/puppet-server/stargazers",
                               :issues_url
                                                  "https://api.github.com/repos/KevinCorcoran/puppet-server/issues{/number}",
                               :trees_url
                                                  "https://api.github.com/repos/KevinCorcoran/puppet-server/git/trees{/sha}",
                               :events_url
                                                  "https://api.github.com/repos/KevinCorcoran/puppet-server/events",
                               :branches_url
                                                  "https://api.github.com/repos/KevinCorcoran/puppet-server/branches{/branch}",
                               :url               "https://api.github.com/repos/KevinCorcoran/puppet-server",
                               :downloads_url
                                                  "https://api.github.com/repos/KevinCorcoran/puppet-server/downloads",
                               :forks             0,
                               :subscribers_url
                                                  "https://api.github.com/repos/KevinCorcoran/puppet-server/subscribers",
                               :full_name         "KevinCorcoran/puppet-server",
                               :blobs_url
                                                  "https://api.github.com/repos/KevinCorcoran/puppet-server/git/blobs{/sha}",
                               :subscription_url
                                                  "https://api.github.com/repos/KevinCorcoran/puppet-server/subscription",
                               :fork              true,
                               :pushed_at         "2014-10-15T22:22:04Z",
                               :owner
                                                  {:html_url    "https://github.com/KevinCorcoran",
                                                   :gravatar_id "",
                                                   :followers_url
                                                                "https://api.github.com/users/KevinCorcoran/followers",
                                                   :subscriptions_url
                                                                "https://api.github.com/users/KevinCorcoran/subscriptions",
                                                   :site_admin  false,
                                                   :following_url
                                                                "https://api.github.com/users/KevinCorcoran/following{/other_user}",
                                                   :type        "User",
                                                   :received_events_url
                                                                "https://api.github.com/users/KevinCorcoran/received_events",
                                                   :login       "KevinCorcoran",
                                                   :organizations_url
                                                                "https://api.github.com/users/KevinCorcoran/orgs",
                                                   :id          2945462,
                                                   :events_url
                                                                "https://api.github.com/users/KevinCorcoran/events{/privacy}",
                                                   :url         "https://api.github.com/users/KevinCorcoran",
                                                   :repos_url   "https://api.github.com/users/KevinCorcoran/repos",
                                                   :starred_url
                                                                "https://api.github.com/users/KevinCorcoran/starred{/owner}{/repo}",
                                                   :gists_url
                                                                "https://api.github.com/users/KevinCorcoran/gists{/gist_id}",
                                                   :avatar_url
                                                                "https://avatars.githubusercontent.com/u/2945462?v=2"},
                               :git_tags_url
                                                  "https://api.github.com/repos/KevinCorcoran/puppet-server/git/tags{/sha}",
                               :created_at        "2014-10-08T22:38:13Z",
                               :mirror_url        nil}},
    :diff_url
                      "https://github.com/puppetlabs/puppet-server/pull/224.diff",
    :comments_url
                      "https://api.github.com/repos/puppetlabs/puppet-server/issues/224/comments",
    :locked           false,
    :id               22756308,
    :url
                      "https://api.github.com/repos/puppetlabs/puppet-server/pulls/224",
    :base
                      {:label "puppetlabs:master",
                       :ref   "master",
                       :sha   "cf3874cdf9634aba31af0ef10ac2a635a707c839",
                       :user
                              {:html_url    "https://github.com/puppetlabs",
                               :gravatar_id "",
                               :followers_url
                                            "https://api.github.com/users/puppetlabs/followers",
                               :subscriptions_url
                                            "https://api.github.com/users/puppetlabs/subscriptions",
                               :site_admin  false,
                               :following_url
                                            "https://api.github.com/users/puppetlabs/following{/other_user}",
                               :type        "Organization",
                               :received_events_url
                                            "https://api.github.com/users/puppetlabs/received_events",
                               :login       "puppetlabs",
                               :organizations_url
                                            "https://api.github.com/users/puppetlabs/orgs",
                               :id          234268,
                               :events_url
                                            "https://api.github.com/users/puppetlabs/events{/privacy}",
                               :url         "https://api.github.com/users/puppetlabs",
                               :repos_url   "https://api.github.com/users/puppetlabs/repos",
                               :starred_url
                                            "https://api.github.com/users/puppetlabs/starred{/owner}{/repo}",
                               :gists_url
                                            "https://api.github.com/users/puppetlabs/gists{/gist_id}",
                               :avatar_url
                                            "https://avatars.githubusercontent.com/u/234268?v=2"},
                       :repo
                              {:html_url          "https://github.com/puppetlabs/puppet-server",
                               :description       "Server automation framework and application",
                               :open_issues_count 2,
                               :watchers          62,
                               :ssh_url           "git@github.com:puppetlabs/puppet-server.git",
                               :hooks_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/hooks",
                               :archive_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/{archive_format}{/ref}",
                               :keys_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/keys{/key_id}",
                               :forks_count       19,
                               :languages_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/languages",
                               :git_url           "git://github.com/puppetlabs/puppet-server.git",
                               :issue_comment_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/issues/comments/{number}",
                               :git_refs_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/git/refs{/sha}",
                               :clone_url         "https://github.com/puppetlabs/puppet-server.git",
                               :contents_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/contents/{+path}",
                               :has_downloads     true,
                               :teams_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/teams",
                               :has_issues        false,
                               :issue_events_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/issues/events{/number}",
                               :private           false,
                               :watchers_count    62,
                               :collaborators_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/collaborators{/collaborator}",
                               :homepage          "https://tickets.puppetlabs.com/browse/SERVER",
                               :git_commits_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/git/commits{/sha}",
                               :name              "puppet-server",
                               :releases_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/releases{/id}",
                               :milestones_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/milestones{/number}",
                               :svn_url           "https://github.com/puppetlabs/puppet-server",
                               :merges_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/merges",
                               :compare_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/compare/{base}...{head}",
                               :stargazers_count  62,
                               :tags_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/tags",
                               :statuses_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/statuses/{sha}",
                               :notifications_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/notifications{?since,all,participating}",
                               :open_issues       2,
                               :has_wiki          true,
                               :size              5300,
                               :assignees_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/assignees{/user}",
                               :commits_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/commits{/sha}",
                               :labels_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/labels{/name}",
                               :forks_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/forks",
                               :contributors_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/contributors",
                               :updated_at        "2014-10-12T16:53:41Z",
                               :pulls_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/pulls{/number}",
                               :has_pages         false,
                               :default_branch    "master",
                               :language          "Clojure",
                               :comments_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/comments{/number}",
                               :id                19546488,
                               :stargazers_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/stargazers",
                               :issues_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/issues{/number}",
                               :trees_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/git/trees{/sha}",
                               :events_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/events",
                               :branches_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/branches{/branch}",
                               :url               "https://api.github.com/repos/puppetlabs/puppet-server",
                               :downloads_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/downloads",
                               :forks             19,
                               :subscribers_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/subscribers",
                               :full_name         "puppetlabs/puppet-server",
                               :blobs_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/git/blobs{/sha}",
                               :subscription_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/subscription",
                               :fork              false,
                               :pushed_at         "2014-10-15T21:30:13Z",
                               :owner
                                                  {:html_url    "https://github.com/puppetlabs",
                                                   :gravatar_id "",
                                                   :followers_url
                                                                "https://api.github.com/users/puppetlabs/followers",
                                                   :subscriptions_url
                                                                "https://api.github.com/users/puppetlabs/subscriptions",
                                                   :site_admin  false,
                                                   :following_url
                                                                "https://api.github.com/users/puppetlabs/following{/other_user}",
                                                   :type        "Organization",
                                                   :received_events_url
                                                                "https://api.github.com/users/puppetlabs/received_events",
                                                   :login       "puppetlabs",
                                                   :organizations_url
                                                                "https://api.github.com/users/puppetlabs/orgs",
                                                   :id          234268,
                                                   :events_url
                                                                "https://api.github.com/users/puppetlabs/events{/privacy}",
                                                   :url         "https://api.github.com/users/puppetlabs",
                                                   :repos_url   "https://api.github.com/users/puppetlabs/repos",
                                                   :starred_url
                                                                "https://api.github.com/users/puppetlabs/starred{/owner}{/repo}",
                                                   :gists_url
                                                                "https://api.github.com/users/puppetlabs/gists{/gist_id}",
                                                   :avatar_url
                                                                "https://avatars.githubusercontent.com/u/234268?v=2"},
                               :git_tags_url
                                                  "https://api.github.com/repos/puppetlabs/puppet-server/git/tags{/sha}",
                               :created_at        "2014-05-07T19:00:24Z",
                               :mirror_url        nil}},
    :_links
                      {:self
                        {:href
                          "https://api.github.com/repos/puppetlabs/puppet-server/pulls/224"},
                       :html
                        {:href "https://github.com/puppetlabs/puppet-server/pull/224"},
                       :issue
                        {:href
                          "https://api.github.com/repos/puppetlabs/puppet-server/issues/224"},
                       :comments
                        {:href
                          "https://api.github.com/repos/puppetlabs/puppet-server/issues/224/comments"},
                       :review_comments
                        {:href
                          "https://api.github.com/repos/puppetlabs/puppet-server/pulls/224/comments"},
                       :review_comment
                        {:href
                          "https://api.github.com/repos/puppetlabs/puppet-server/pulls/comments/{number}"},
                       :commits
                        {:href
                          "https://api.github.com/repos/puppetlabs/puppet-server/pulls/224/commits"},
                       :statuses
                        {:href
                          "https://api.github.com/repos/puppetlabs/puppet-server/statuses/21f02f8e20320a08e2d3f6520df01978a5f5cda3"}},
    :body
                      "* The execution stub now returns an instance of\r\n  Puppet::Util::Execution::ProcessOutput, as per the contract of\r\n  Puppet::Util::Execution.execute.\r\n\r\n* Add basic spec/unit tests\r\n\r\n* In java, close the reader used to read the process's STDOUT.\r\n\r\n* In ruby, close the file handles passed-in for STDIN, STDOUT, and\r\n  STDERR; we're not using these (this is implemented in java instead),\r\n  but Puppet::Util::Execution.execute does not close them when an\r\n  execution stub is installed.",
    :user
                      {:html_url    "https://github.com/KevinCorcoran",
                       :gravatar_id "",
                       :followers_url
                                    "https://api.github.com/users/KevinCorcoran/followers",
                       :subscriptions_url
                                    "https://api.github.com/users/KevinCorcoran/subscriptions",
                       :site_admin  false,
                       :following_url
                                    "https://api.github.com/users/KevinCorcoran/following{/other_user}",
                       :type        "User",
                       :received_events_url
                                    "https://api.github.com/users/KevinCorcoran/received_events",
                       :login       "KevinCorcoran",
                       :organizations_url
                                    "https://api.github.com/users/KevinCorcoran/orgs",
                       :id          2945462,
                       :events_url
                                    "https://api.github.com/users/KevinCorcoran/events{/privacy}",
                       :url         "https://api.github.com/users/KevinCorcoran",
                       :repos_url   "https://api.github.com/users/KevinCorcoran/repos",
                       :starred_url
                                    "https://api.github.com/users/KevinCorcoran/starred{/owner}{/repo}",
                       :gists_url
                                    "https://api.github.com/users/KevinCorcoran/gists{/gist_id}",
                       :avatar_url
                                    "https://avatars.githubusercontent.com/u/2945462?v=2"},
    :review_comments_url
                      "https://api.github.com/repos/puppetlabs/puppet-server/pulls/224/comments",
    :assignee         nil,
    :created_at       "2014-10-15T01:51:17Z"}))

(defn pulls-mock-with-prs
  "Mock response for tenticles.pulls/pulls which includes 2 open PR's."
  [_ _ _]
  pulls-response)

(defn pulls-mock-with-no-prs
  "Mock response for tenticles.pulls/pulls which includes no open PR's."
  [_ _ _]
  '({:X-RateLimit-Remaining "56", :X-RateLimit-Limit "60"}))



