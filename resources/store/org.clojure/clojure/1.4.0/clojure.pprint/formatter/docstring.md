  Makes a function which can directly run format-in. The function is
fn [stream & args] ... and returns nil unless the stream is nil (meaning 
output to a string) in which case it returns the resulting string.

format-in can be either a control string or a previously compiled format.