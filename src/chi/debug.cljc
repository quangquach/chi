(ns chi.debug)

(defmacro log
  "Print with console.log, if it exists."
  [& forms]
  `(when js/console
     (.log js/console ~@forms)))
