(defn list-model [provider]
  (let [{:keys [nrows get-value get-label]} provider]
    (proxy [AbstractTableModel] []
      (getColumnCount [] 2)
      (getRowCount [] nrows)
      (getValueAt [rowIndex columnIndex]
        (cond 
         (= 0 columnIndex) (get-label rowIndex)
         (= 1 columnIndex) (print-str (get-value rowIndex)))))))