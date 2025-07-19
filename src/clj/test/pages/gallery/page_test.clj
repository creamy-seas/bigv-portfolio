(ns pages.gallery.page-test
  (:require [clojure.test :refer [testing deftest]]
            [pages.gallery.page]))

(deftest valid-read-gallery
  (testing "good read of csv"
    (pages.gallery.page/read-gallery)))
