(ns grimoire.web.views.content.txt
  (:require [grimoire.web.views :refer :all]
            [grimoire.api :as api]
            [grimoire.things :as t]
            [grimoire.either :refer [succeed? result]]
            [grimoire.web.config :as cfg]
            [ring.util.response :as response]))

(defmethod store-page :text/plain [_]
  (-> (str "# Store\n\n"
          "Sorry, this page isn't supported in plaintext mode :c\n"
          "Feel free to file an issue on the bugtracker if you think this could be useful\n")
     response/response
     (response/content-type "text/plain")))

(defmethod group-page :text/plain [_ group-thing]
  (-> (str "# Group " (:name group-thing) "\n\n"
          "Sorry, this page isn't supported in plaintext mode :c\n"
          "Feel free to file an issue on the bugtracker if you think this could be useful\n")
     response/response
     (response/content-type "text/plain")))

(defmethod artifact-page :text/plain [_ artifact-thing]
  (-> (str "# Artifact " (:uri artifact-thing) "\n\n"
          "Sorry, this page isn't supported in plaintext mode :c\n"
          "Feel free to file an issue on the bugtracker if you think this could be useful\n")
     response/response
     (response/content-type "text/plain")))

(defmethod version-page :text/plain [_ version-thing]
  (-> (str "# Version " (:uri version-thing) "\n\n"
          "Sorry, this page isn't supported in plaintext mode :c\n"
          "Feel free to file an issue on the bugtracker if you think this could be useful\n")
     response/response
     (response/content-type "text/plain")))

(defmethod namespace-page :text/plain [_ namespace-thing]
  (-> (str "# Namespace " (:uri namespace-thing) "\n\n"
          "Sorry, this page isn't supported in plaintext mode :c\n"
          "Feel free to file an issue on the bugtracker if you think this could be useful\n")
     response/response
     (response/content-type "text/plain")))

(defmethod symbol-page :text/plain [_ def-thing]
  (let [*cfg*      (cfg/lib-grim-config)
        groupid    (t/thing->group     def-thing)
        artifactid (t/thing->artifact  def-thing)
        version    (t/thing->version   def-thing)
        namespace  (t/thing->namespace def-thing)
        line80     (apply str (repeat 80 "-"))
        line40     (apply str (repeat 40 "-"))
        ?meta      (api/read-meta *cfg* def-thing)]
    (-> (if (succeed? ?meta)
          (try
            (let [{:keys [doc name type arglists src]
                   :as   meta} (result ?meta)
                   ?notes      (api/read-notes    *cfg* def-thing)
                   ?related    (api/list-related  *cfg* def-thing)
                   ?examples   (api/list-examples *cfg* def-thing)]
              ;; FIXME: else what? doesn't make sense w/o doc...
              (str (format "# [%s/%s \"%s\"] %s/%s\n"
                           (t/thing->name groupid)
                           (t/thing->name artifactid)
                           (t/thing->name version)
                           (t/thing->name namespace)
                           name)
                   ;; line80
                   "\n"

                   (when-not (empty? arglists)
                     (str (if-not (= type :special)
                            "## Usages\n"
                            "## Arities\n")
                          ;; line40 "\n"
                          (->> arglists
                               (map #(format "  %s\n" (pr-str %1)))
                               (apply str))
                          "\n"))

                   (when doc
                     (str "## Documentation\n"
                          ;; line40 "\n"
                          "  " doc
                          "\n\n"))

                   (when (succeed? ?notes)
                     (when-let [notes (result ?notes)]
                       (str "## User Documentation\n"
                            ;; line40 "\n"
                            (->> (for [[v n] notes] n)
                                 (apply str))
                            "\n\n")))

                   (when (succeed? ?examples)
                     (when-let [examples (result ?examples)]
                       (str "## Examples\n"
                            ;; line40 "\n"
                            (->> (for [e-thing examples
                                       :let [ex (result (api/read-example *cfg* e-thing))
                                             ex (if-not (.endsWith ex "\n")
                                                  (str ex "\n") ex)]]
                                   (str "```Clojure\n"
                                        ex
                                        "```"))
                                 (interpose "\n\n")
                                 (apply str "\n"))
                            "\n\n")))

                   (when (succeed? ?related)
                     (let [related (result ?related)]
                       (when-not (empty? related)
                         (str "## See Also\n"
                              ;; line40 "\n"
                              (->> related
                                   (map str)
                                   (apply str))
                              "\n\n"))))))

            (catch AssertionError e
              (str "# " (:uri def-thing) "\n\n"
                   "Well shit... something went wrong :c\n"
                   "Please file a bug report at https://github.com/clojure-grimoire/grimoire <3\n\n"
                   "## Message\n"
                   "    "(.getMessage e) "\n\n"
                   "## Stack trace\n"
                   (->> (.getStackTrace e)
                        (map #(str "    " % "\n"))
                        (apply str))
                   "\n")))

          (str "# " (:uri def-thing) "\n\n"
               "No such symbol :c\n"
               "Please file a bug report at https://github.com/clojure-grimoire/grimoire <3\n\n"))

        response/response
        (response/content-type "text/plain"))))
