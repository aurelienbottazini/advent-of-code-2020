(ns day11)

(defn parse-input [filepath]
  (->> filepath slurp clojure.string/split-lines))

(def example (parse-input "./src/day11.example.txt"))
(def input (parse-input "./src/day11.input.txt"))

(defn next-seat-state [coll x y]
  (let [left (get-in coll [y (dec x)])
        right (get-in coll [y (inc x)])
        up (get-in coll [(dec y) x])
        down (get-in coll [(inc y) x])
        left-up (get-in coll [(dec y) (dec x)])
        left-down (get-in coll [(inc y) (dec x)])
        right-up (get-in coll [(dec y) (inc x)])
        right-down (get-in coll [(inc y) (inc x)])
        current (get-in coll [y x])
        adjacent-squares [left right up down left-down left-up right-down right-up]
        occupied-adjacents (count (filter true? (keep #(= \# %) adjacent-squares)))
        ]
    (cond
      (and (= current \L) (= 0 occupied-adjacents)) \#
      (and (= current \#) (> occupied-adjacents 3)) \L
      :else current)))

(defn next-layout [coll]
  (into []
        (map-indexed (fn [y ys] (into [] (map-indexed (fn [x _] (next-seat-state coll x y)) ys)))
                     coll)))

(defn occupied-seats [seats]
  (reduce +
          (map (fn [row] (count (filter true? (keep #(= \# %) row))))
               (loop [count 1
                      coll (next-layout seats)
                      next-coll (next-layout coll)
                      ]
                 (if (= coll next-coll)
                   coll (recur (inc count) next-coll (next-layout next-coll)))))))

(occupied-seats input)




