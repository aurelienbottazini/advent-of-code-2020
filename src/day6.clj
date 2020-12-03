(ns day6)

(def example (clojure.string/split "abc

a
b
c

ab
ac

a
a
a
a

b" #"\n\n"))

(def scores (clojure.string/split (slurp "./src/day6.input.txt") #"\n\n"))

(defn group-score [group step]
  (count (reduce step (map set (clojure.string/split-lines group)))))

;; part 1
(apply + (map #(group-score % clojure.set/union) example))
(apply + (map #(group-score % clojure.set/union) scores))

;; part 2
(apply + (map #(group-score % clojure.set/intersection) example))
(apply + (map #(group-score % clojure.set/intersection) scores))
