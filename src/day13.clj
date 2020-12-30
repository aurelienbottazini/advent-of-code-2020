(ns day13)

(defn parse-input [path]
  (let [input (->> path slurp clojure.string/split-lines)]
    [(clojure.edn/read-string (first input))
     (->> input rest first
          (#(clojure.string/split % #","))
          (filter #(not= "x" %))
          (map #(clojure.edn/read-string %)))]))

(def example (parse-input "./src/day13.example"))
(def input (parse-input "./src/day13.input"))

(defn closest-bus-time
  ([time increment] (closest-bus-time time increment 0))
  ([time increment bus-time]
   (cond
     (>= bus-time time) [increment bus-time]
     :else (recur time increment (+ bus-time increment)))))

(let [time (first input)
      bus-ids (->> input rest first)
      bus-with-time (first
       (sort-by #(->> % rest first)
                (map #(closest-bus-time time %) bus-ids)))]
  (* (get bus-with-time 0)
     (- (get bus-with-time 1) time)))

;; part 2

(defn parse-input2 [path]
  (let [input (->> path slurp clojure.string/split-lines)]
    [(clojure.edn/read-string (first input))
     (->> input rest first
          (#(clojure.string/split % #","))
          (map #(clojure.edn/read-string %)))]))

(def input2 (parse-input2 "./src/day13.input"))

(defn next-bus [[sum product] [index bus-id]]
  (loop [sum sum]
    (if (zero? (mod (+ sum index) bus-id))
      [sum (* product bus-id)]
      (recur (+ sum product)))))

(->> input2 rest first
     (interleave (range))
     (partition 2)
     (map (fn [[index bus-id]]
            (if (not= 'x bus-id)
              [index bus-id])))
     (remove nil?)
     (reduce next-bus)
     first)
