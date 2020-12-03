(ns day5)

(def example "FBFBBFFRLR")
(def example1 "BFFFBBFRRR")
(def example2 "FFFBBBFRRR")
(def example3 "BBFFBBFRLL")

(def seats (clojure.string/split-lines (slurp "./src/day5.input.txt")))

(defn bs [alist min max char]
  (cond
    (empty? alist) (clojure.core/min min max)
    (= char (first alist)) (recur (drop 1 alist) min (int (/ (+ min max) 2)) char)
    :else (recur (drop 1 alist) (int (Math/ceil (/ (+ min max) 2))) max char)))

(defn seat-id [alist]
  (+ (* 8 (bs (take 7 alist) 0 127 \F))
     (bs (drop 7 alist) 0 7 \L)))

(seat-id example)
(seat-id example1)
(seat-id example2)
(seat-id example3)

(apply max (map seat-id seats))

(loop [seat-ids (sort (map seat-id seats))]
  (cond
    (< (count seat-ids) 2) :not_found
    (= (first seat-ids) (- (first (rest seat-ids)) 2)) (+ 1 (first seat-ids))
    :else (recur (rest seat-ids))))
