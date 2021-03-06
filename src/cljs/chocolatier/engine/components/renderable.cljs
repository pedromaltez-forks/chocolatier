(ns chocolatier.engine.components.renderable
  (:use [chocolatier.utils.logging :only [debug error]]))


(defn update-sprite
  [entity-id component-state inbox]
  (let [move-events (filter #(= (:event-id %) :move) inbox)
        ;; Offsets come from messages in the inbox and are aggregated
        {:keys [move-x move-y]
         :or {move-x 0 move-y 0}} (apply merge-with + (map :msg move-events))
        sprite (:sprite component-state)
        {:keys [pos-x pos-y]} component-state
        updated-state (assoc component-state
                          :pos-x (- pos-x move-x)
                          :pos-y (- pos-y move-y))]
    ;; Mutate the x and y position
    (set! (.-position.x sprite) (:pos-x updated-state))
    (set! (.-position.y sprite) (:pos-y updated-state))
    updated-state))
