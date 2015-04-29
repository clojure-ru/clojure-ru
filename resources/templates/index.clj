(static.core/define-template index-template-file

  [:html] general-template

  [:head :title] (enlive/content (:site-title (config)))

  [:#news] (enlive/do->
            (enlive/content (map template-news-block-model
                                 (reverse (take number-of-news-on-home content))))
            (enlive/append (when (> (count content) number-of-news-on-home)
                                  (template-other-news-block-model 
                                    (drop number-of-news-on-home (reverse content))))))

  ;[:.hero-example :.highlight] (enlive/html-content (:content (first content)))

  ;[:.debug] (fn [_] (:content (first content)))
)
