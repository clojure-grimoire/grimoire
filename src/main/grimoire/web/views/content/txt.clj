(ns grimoire.web.views.content.txt
  (:require [grimoire.web.views :refer :all]
            [clojure.java.io :as io] ; FIXME: remove
            [clojure.string :as string]
            [grimoire.github :as gh] ; FIXME: remove
            [grimoire.util :as util]
            [grimoire.api :as api]
            [grimoire.api.fs] ;; needed to load it
            [grimoire.api.fs.read]
            [grimoire.things :as t]
            [grimoire.web.layout :refer [layout]]
            [grimoire.web.util :as wutil :refer [clojure-versions]] ; FIXME: remove
            [ring.util.response :as response]
            [detritus.text :as text]))

(defmethod symbol-page :text/plain [_ def-thing]
  (let [groupid      (t/thing->group     def-thing)
        artifactid   (t/thing->artifact  def-thing)
        version      (t/thing->version   def-thing)
        namespace    (t/thing->namespace def-thing)

        {:keys [doc name type arglists src]
         :as   meta} (api/read-meta site-config def-thing)

        doc          (-> doc
                       text/text->paragraphs
                       text/render)

        notes        (-> site-config (api/read-notes def-thing)) ;; Seq [version, notes]

        related      (-> site-config
                       (api/read-related def-thing))  ;; Seq [version, related]

        line80 (apply str (repeat 80 "-"))
        line40 (apply str (repeat 40 "-"))]
    (when doc
      ;; FIXME: else what? doesn't make sense w/o doc...
      ;; FIXME: conditionalize _all_ headers
      (-> (str (format "# [%s/%s \"%s\"] %s/%s\n" groupid artifactid version namespace name)
              ;; line80
              "\n"

              "## Arities\n"
              ;; line40 "\n"
              (->> (map #(format "  %s\n" (pr-str %1)))
                (apply str))
              "\n"

              "## Documentation\n"
              ;; line40 "\n"
              doc
              "\n"

              "## User Documentation\n"
              ;; line40 "\n"
              notes
              "\n"

              "## Examples\n"
              ;; line40 "\n"
              "FIXME: This version of Grimoire can't read examples for text format!\n"

              "## See Also\n"
              ;; line40 "\n"
              related)
        response/response
        (response/content-type "text/plain")))))
