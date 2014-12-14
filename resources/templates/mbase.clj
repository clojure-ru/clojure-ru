; This contains base defintions that will be available in all enlive templates.
; They will be registered in static.core/

; metadata is a dictionary of
; :categories -> {:name :url} all the tags in the project
; :projects -> {:name :url} all the sites / projects under sites
; :config -> the site config dictionary
; :type can be :post or :site or :nopost (?)
; :pager -> {:older "link" :newer "link"} (only on pages with pages)

; content is a vector of dictionaries:
; :id
; :title
; :ent
; :url
; :content
; :keywords / tags
; :footnotes

(def base-template-file (static.core/template-path "_first.html"))
(def site-template-file (static.core/template-path "_second.html"))

(def number-of-news-on-home "This number most 3*k-1" 5)
(def number-of-other-news-on-home "Less than 5" 3)

(defn is-home? []
  (not (:type metadata)))

(defn only-for-home [node]
  (when-not (:type metadata) node))

(defn page-title [header]
   (enlive/at header [:h1] (enlive/content (:title metadata))))

(defn make-link [title url]
  (enlive/do->
    (enlive/set-attr :href url)
    (enlive/content title)))

(defn links-list [posts]
  (enlive/clone-for [{:keys [title url]} posts]
    [:a] (make-link title url)))

;; NEWS BLOCK FOR HOME

(enlive/defsnippet template-news-block-model base-template-file
 [:.post.cell]
 [{:keys [date title url description] :as post}]
 [:h3 :a] (make-link title url)
 [:p.post-info] (enlive/content date)
 [:p :a] (enlive/set-attr :href url)
 [:div :div] (enlive/html-content description))

(enlive/defsnippet template-other-news base-template-file
 [:#news :> enlive/last-child :li]
 [{:keys [title url]}]
 [:a] (make-link title url))

(enlive/defsnippet template-other-news-block-model base-template-file
 [:#news  :> enlive/last-child]
 [content]
 [:.post :ul :li] (links-list (take number-of-other-news-on-home content)))

(enlive/defsnippet template-recent-post site-template-file
  [:.content.standard-layout :.sidebar :.navigation]
  []
  [:h4 :strong] (enlive/content "Последние новости")
  [:ul.menu :li] (links-list (recent-posts-sidebar)))

(defn make-archive-links-for-year [year date]
    {:title (str (format-date "MMMM" date) " " year)
     :url (str "/" year "/" (format-date  "MM" date))})

(defn make-archive-links-for-archive [[year _]]
    {:title (str year " Архивы") :url (str "/" year "/")})

(defn archive-sidebar-title []
  (if (= (:type metadata) :year-news)
      "Архив за месяц"
      "Архив за год"))

(enlive/defsnippet template-archive-post site-template-file
  [:.content.standard-layout :.sidebar :.navigation]
  []
  [:h4 :strong] (enlive/content (archive-sidebar-title))
  [:ul.menu :li]
  (if (= (:type metadata) :year-news)
    (let [[year posts] (first content)]
      (links-list (map #(make-archive-links-for-year year %) 
                       (distinct (map :javadate posts)))))
    (links-list (map make-archive-links-for-archive content)))) 
