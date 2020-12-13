(ns day12)

(defn parse-input [path]
  (->> path slurp clojure.string/split-lines
       (map #(vector (subs % 0 1)
                     (clojure.edn/read-string (subs % 1))))))

(def example (parse-input "./src/day12.example.txt"))
(def input (parse-input "./src/day12.input.txt"))

(def start-position
  {"E" 0 "S" 0 "W" 0 "N" 0 :facing "E"})

(def right (cycle ["E" "S" "W" "N"]))
(def right-offset {"E" 0 "S" 1 "W" 2 "N" 3})
(def left (cycle ["E" "N" "W" "S"]))
(def left-offset {"E" 0 "N" 1 "W" 2 "S" 3})

(defn turn [direction turn-direction val]
  (let [steps (mod (/ val 90) 4)]
    (if (= turn-direction "L")
      (nth left (+ steps (get left-offset direction)))
      (nth right (+ steps (get right-offset direction))))))

(defn move [[direction val] position]
  (cond
    (= direction "F") (update-in position [(:facing position)] + val)
    (or (= direction "E")
        (= direction "S")
        (= direction "W")
        (= direction "N")) (update-in position [(str direction)] + val)
    :else (assoc position :facing (turn (:facing position) direction val))))

(defn navigate [coll position]
  (cond
    (empty? coll) position
    :else (recur (rest coll) (move (first coll) position))))

(let [position (navigate input start-position)]
  (+ (Math/abs (- (get position "S")
                  (get position "N")))
     (Math/abs (- (get position "E")
                  (get position "W")))))
