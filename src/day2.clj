(ns day2 (:require [clojure.string :refer [split-lines split]]))

(defn parse-input [input]
  (map #(list (map (fn [x] (Integer/parseInt x)) (split (first (first %)) #"-")) (first (rest (first %))) (first (rest %)))
       (map #(list (split (first %) #" ") (first (rest %)))
            (map #(split % #": ") (split-lines input)))))

(def example (parse-input "1-3 a: abcde
1-3 b: cdefg
2-9 c: ccccccccc"))

(def passwords-with-policies
  (parse-input (slurp "./src/day2.input.txt")))

(defn check-password [password-with-policy]
  (let [[[min max] char password] password-with-policy]
    (<= min (get (frequencies password) (first char) 0) max)))

(count (filter identity (map check-password example)))
(count (filter identity (map check-password passwords-with-policies)))

(defn check-password-new-policy [password-with-policy]
  (let [[[first second] char password] password-with-policy]
    (= 1 (count (filter identity (list (= (get char 0) (get password (- first 1)))
                                       (= (get char 0) (get password (- second 1)))))))))

(count (filter identity (map check-password-new-policy example)))
(count (filter identity (map check-password-new-policy passwords-with-policies)))
