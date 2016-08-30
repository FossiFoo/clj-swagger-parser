(ns clj-swagger-parser.context
  (:require [clojure.java.io :as io]
            [clj-yaml.core :as yaml]
            [cheshire.core :as json]
            [schema.core :as schema]
            [clj-swagger-parser.schemas.swagger-2-0 :as swagger-2-0]))

(defmulti load-swagger-definition "Loads a swagger definition from given source."
          (fn [definition-type _] definition-type))

(defmethod load-swagger-definition :direct [_ definition]
  definition)

(defmethod load-swagger-definition :json [_ definition]
  (json/decode definition))

(defmethod load-swagger-definition :json-file [_ definition]
  (load-swagger-definition :json (slurp definition)))

(defmethod load-swagger-definition :json-cp [_ definition]
  (load-swagger-definition :json-file (io/resource definition)))

(defmethod load-swagger-definition :yaml [_ definition]
  (yaml/parse-string definition :keywords false))

(defmethod load-swagger-definition :yaml-file [_ definition]
  (load-swagger-definition :yaml (slurp definition)))

(defmethod load-swagger-definition :yaml-cp [_ definition]
  (load-swagger-definition :yaml-file (io/resource definition)))

(defn create-definition
  "Loads a swagger definition and produces a context containing the validated and loaded definition."
  [swagger-definition-type swagger-definition validate?]
  (let [definition (load-swagger-definition swagger-definition-type swagger-definition)]
    (when validate?
      (schema/validate swagger-2-0/root-object definition))
    definition))
