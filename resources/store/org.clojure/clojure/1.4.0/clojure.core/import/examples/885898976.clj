;; importing multiple classes in a namespace
(ns foo.bar
  (:import (java.util Date
                      Calendar)
           (java.util.logging Logger
                              Level)))