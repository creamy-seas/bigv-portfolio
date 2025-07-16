(ns pages.gallery.page-test
  (:require [clojure.test :refer :all]
            [pages.gallery.page]))

(deftest valid-read-gallery
  (testing "good read of csv"
    (pages.gallery.page/read-gallery)))
