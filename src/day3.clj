(ns day3
  (:require [clojure.string :refer [split-lines]]))

(def example (split-lines "..##.......
#...#...#..
.#....#..#.
..#.#...#.#
.#...##..#.
..#.##.....
.#.#.#....#
.#........#
#.##...#...
#...##....#
.#..#...#.#"))

(def area-map (split-lines
 (slurp "./src/day3.input.txt")))

(defn square [line move-right number-of-steps]
  (if (<= (count line) (* move-right number-of-steps))
    (recur (str line line) move-right number-of-steps)
    (get line (* move-right number-of-steps))))

(defn move-map [move-down area-map]
  (drop move-down area-map))

(defn slide-toboggan [area-map number-of-trees number-of-steps moves]
  (cond
    (empty? area-map) number-of-trees
    :else (if (= \# (square (first area-map) (:right moves) number-of-steps))
            (recur (move-map (:down moves) area-map) (+ 1 number-of-trees) (+ 1 number-of-steps) moves)
            (recur (move-map (:down moves) area-map) number-of-trees (+ 1 number-of-steps) moves))))

(slide-toboggan example 0 0 {:right 3 :down 1})
(slide-toboggan area-map 0 0 {:right 3 :down 1})

(apply * (map #(slide-toboggan area-map 0 0 %)
              '({:right 1 :down 1}
                {:right 3 :down 1}
                {:right 5 :down 1}
                {:right 7 :down 1}
                {:right 1 :down 2})))
