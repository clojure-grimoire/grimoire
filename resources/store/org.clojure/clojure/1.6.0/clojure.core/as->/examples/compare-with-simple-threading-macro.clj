; Using the simple threading macro ->
(-> shape
  (rotate 30)
  (scale 1.2)
  (translate 10 -2))

; Using as->
(as-> shape s
  (rotate s 30)
  (scale s 1.2)
  (translate s 10 -2))
