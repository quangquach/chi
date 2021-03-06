(ns chi.frontend.paragraphs-test
  (:require #?(:cljs [cljs.test    :as t :refer-macros [deftest testing]]
               :clj  [clojure.test :as t :refer        [deftest testing]])
            #?(:cljs [chi.test-support :refer [assert-node]]
               :clj  [chi.assert-macros :refer [assert-node]])
            [chi.frontend.parser :refer [lines->ast]]))

#?(:cljs (enable-console-print!))

(deftest single-line-paragraph
  (let [lines ["Lorem Ipsum is simply dummy text"]
        root (lines->ast lines)]
    (assert-node {:type :root :children [:children-count 1 count]} root)

    (let [[paragraph] (:children root)]
      (assert-node {:type :paragraph
                    :children [{:type :text
                                :value "Lorem Ipsum is simply dummy text"}]}
                   paragraph))))

(deftest multilines-paragraph
  (let [lines ["Lorem Ipsum is simply dummy text of"
               "the printing and typesetting industry."]
        root (lines->ast lines)]
    (assert-node {:type :root :children [:children-count 1 count]} root)


    (let [[paragraph] (:children root)]
      (assert-node {:type :paragraph
                    :children [{:type :text
                                :value (str "Lorem Ipsum is simply dummy text of "
                                            "the printing and typesetting industry.")}]}
                   paragraph))))

(deftest multi-paragraphs
  (let [lines ["Lorem Ipsum is simply dummy text of"
               "the printing and typesetting industry."
               ""
               "Lorem Ipsum has been the industry's standard dummy text ever since..."]
        root (lines->ast lines)]
    (assert-node {:type :root :children [:children-count 2 count]} root)

    (let [[paragraph-1 paragraph-2] (:children root)]
      (assert-node {:type :paragraph :children [{:type :text
                                                 :value (str "Lorem Ipsum is simply dummy text of "
                                                             "the printing and typesetting industry.")}]}
                   paragraph-1)
      (assert-node {:type :paragraph :children [{:type :text
                                                 :value (str "Lorem Ipsum has been the industry's "
                                                             "standard dummy text ever since...")}]}
                   paragraph-2))))
