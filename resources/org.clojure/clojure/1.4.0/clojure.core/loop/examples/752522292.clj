;; Read decoded MP3 data in loop (requires mp3plugin.jar on class path)
;; http://java.sun.com/javase/technologies/desktop/media/jmf/mp3/download.html 

(import '(javax.sound.sampled AudioSystem AudioFormat$Encoding))

(let [mp3-file (java.io.File. "tryout.mp3")
      audio-in (AudioSystem/getAudioInputStream mp3-file)
      audio-decoded-in (AudioSystem/getAudioInputStream AudioFormat$Encoding/PCM_SIGNED audio-in)
      buffer (make-array Byte/TYPE 1024)]
  (loop []
    (let [size (.read audio-decoded-in buffer)]
      (when (> size 0)
        ;do something with PCM data
	(recur)))))
