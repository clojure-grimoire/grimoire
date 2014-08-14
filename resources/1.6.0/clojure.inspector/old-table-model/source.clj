(defn old-table-model [data]
  (let [row1 (first data)
	colcnt (count row1)
	cnt (count data)
	vals (if (map? row1) vals identity)]
    (proxy [TableModel] []
      (addTableModelListener [tableModelListener])
      (getColumnClass [columnIndex] Object)
      (getColumnCount [] colcnt)
      (getColumnName [columnIndex]
	(if (map? row1)
	  (name (nth (keys row1) columnIndex))
	  (str columnIndex)))
      (getRowCount [] cnt)
      (getValueAt [rowIndex columnIndex]
	(nth (vals (nth data rowIndex)) columnIndex))
      (isCellEditable [rowIndex columnIndex] false)
      (removeTableModelListener [tableModelListener]))))