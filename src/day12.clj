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

(time
 (let [position (navigate input start-position)]
   (+ (Math/abs (- (get position "S")
                   (get position "N")))
      (Math/abs (- (get position "E")
                   (get position "W"))))))

(def waypoint-position
  {:x -10 :y 1})

(defn rotate-left [{:keys [x y] :as waypoint} a]
  (case (quot a 90)
    0 waypoint
    1 {:x (- y) :y x}
    2 {:x (- x) :y (- y)}
    3 {:x y     :y (- x)}))

(defn rotate-right [{:keys [x y] :as waypoint} a]
  (case (quot a 90)
    0 waypoint
    1 {:x y :y (- x)}
    2 {:x (- x) :y (- y)}
    3 {:x (- y) :y x}))

(defn move-ship [{sx :x sy :y} {wx :x wy :y} val]
  {:x (+ sx (* val wx))
   :y (+ sy (* val wy))})

(defn move-2 [[direction val] waypoint ship]
  (cond
    (= direction "F") [waypoint (move-ship ship waypoint val)]
    (= direction "N") [(update-in waypoint [:y] + val) ship]
    (= direction "S") [(update-in waypoint [:y] - val) ship]
    (= direction "E") [(update-in waypoint [:x] + val) ship]
    (= direction "W") [(update-in waypoint [:x] - val) ship]
    (= direction "L") [(rotate-left waypoint val) ship]
    (= direction "R") [(rotate-right waypoint val) ship]
    :else (println direction val)))

(defn navigate-2 [coll position waypoint]
  (cond
    (empty? coll) position
    :else (let [[nw np] (move-2 (first coll) waypoint position)]
            (recur (rest coll) np nw))))

(time
 (let [{:keys [x y]} (navigate-2 input {:x 0 :y 0} {:x 10 :y 1})]
   (+ (Math/abs x) (Math/abs y))))

