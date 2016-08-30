(set-env!
  :resource-paths #{"src" "test"}
  :dependencies '[[org.clojure/tools.logging "0.3.1"]

                  [circleci/clj-yaml "0.5.5"]
                  [prismatic/schema "1.0.4"]
                  [clj-time "0.11.0"]
                  [cheshire "5.5.0"]

                  [clj-http "2.0.0"]

                  [adzerk/boot-test "1.1.2" :scope "test"]])

(require '[adzerk.boot-test :refer :all])
