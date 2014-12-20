(ns grimoire.web.views.content.txt
  (:require [grimoire.web.views :refer :all]
            [grimoire.api :as api]
            [grimoire.things :as t]
            [ring.util.response :as response]))

(defmethod symbol-page :text/plain [_ def-thing]
  (let [groupid      (t/thing->group     def-thing)
        artifactid   (t/thing->artifact  def-thing)
        version      (t/thing->version   def-thing)
        namespace    (t/thing->namespace def-thing)

        {:keys [doc name type arglists src]
         :as   meta} (api/read-meta site-config def-thing)
        notes        (api/read-notes site-config def-thing) ;; Seq [version, notes]
        related      (api/read-related site-config def-thing)  ;; Seq [version, related]

        line80 (apply str (repeat 80 "-"))
        line40 (apply str (repeat 40 "-"))]
    (when doc
      ;; FIXME: else what? doesn't make sense w/o doc...
      (-> (str (format "# [%s/%s \"%s\"] %s/%s\n" groupid artifactid version namespace name)
              ;; line80
              "\n"

              (when arglists
                (str "## Arities\n"
                     ;; line40 "\n"
                     (->> arglists
                        (map #(format "  %s\n" (pr-str %1)))
                        (apply str))
                     "\n"))

              (when doc
                (str "## Documentation\n"
                     ;; line40 "\n"
                     doc
                     "\n"))
              
              (when notes
                (str "## User Documentation\n"
                     ;; line40 "\n"
                     notes
                     "\n"))

              "## Examples\n"
              ;; line40 "\n"
              "FIXME: This version of Grimoire can't read examples for text format!\n"

              (when related
                (str "## See Also\n"
                     ;; line40 "\n"
                     related)))
         response/response
         (response/content-type "text/plain")))))
