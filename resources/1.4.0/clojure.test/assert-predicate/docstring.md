  Returns generic assertion code for any functional predicate.  The
  'expected' argument to 'report' will contains the original form, the
  'actual' argument will contain the form with all its sub-forms
  evaluated.  If the predicate returns false, the 'actual' form will
  be wrapped in (not...).