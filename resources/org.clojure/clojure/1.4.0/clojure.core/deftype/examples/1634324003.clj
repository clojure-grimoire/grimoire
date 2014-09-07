user=> (import (java.awt.datatransfer Transferable DataFlavor)
               javax.swing.ImageIcon)

;; create a Transferable Image from an array of bytes
user=> (deftype ImageSelection [data]
         Transferable
         (getTransferDataFlavors
          [this]
          (into-array DataFlavor [DataFlavor/imageFlavor]))
        
         (isDataFlavorSupported
          [this flavor]
          (= DataFlavor/imageFlavor flavor))

         (getTransferData
          [this flavor]
          (when (= DataFlavor/imageFlavor flavor)
            (.getImage (ImageIcon. data)))))

;; create a new image selection:
user=> (def *selection* (ImageSelection. somedata))