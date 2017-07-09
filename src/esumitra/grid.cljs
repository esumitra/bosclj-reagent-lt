;;;; define a grid by clicking grid elements
;;;; calculate offsets and width height
(ns esumitra.grid)

(defn row-col
  "returns vector of [rows cols] for input xs data vector"
  [m n xs]
  (let [rs (partition n xs)
        cs (partition n (apply interleave rs))]
    [rs cs]))

(def positive? #(> % 0))

(defn first-1-index
  "returns index of first 1 occurence in input"
  [xs]
  (first (some #(and (positive? (second %)) %) xs)))

(defn min-max-index
  "returns minimum and maximum index of 1 occurences for input rows/cols"
  [xs]
  (let [ivec (map-indexed
              #(vector %1 %2)
              (map #(if (some positive? %) 1 0) xs))]
    [(first-1-index ivec) (first-1-index (reverse ivec))]))

(defn grid-info
  "returns x/y offset and width/height of grid given input checked data
  for grid with m rows and n cols"
  [m n xs]
  (let [[rs cs] (row-col m n xs)
        [ymin ymax] (min-max-index rs)
        [xmin xmax] (min-max-index cs)]
    {:x-offset xmin
     :y-offset ymin
     :width (if (nil? xmin) xmin (inc (- xmax xmin)))
     :height (if (nil? ymin) ymin (inc (- ymax ymin)))
     :data rs}))
