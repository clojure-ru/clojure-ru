; This contains base defintions that will be available in all enlive templates.
; They will be registered in static.core/

; metadata is a dictionary of
; :categories -> {:name :url} all the tags in the project
; :projects -> {:name :url} all the sites / projects under sites
; :config -> the site config dictionary
; :type can be :post or :site or :nopost or :post-for-tag (?)
; :pager -> {:older "link" :newer "link"} (only on pages with pages)

; content is a vector of dictionaries:
; :id
; :title
; :ent
; :url
; :content
; :keywords / tags
; :footnotes

(def index-template-file (template-path "_index.html"))
(def base-template-file (template-path "_post.html"))
(def site-template-file (template-path "_list.html"))

(def number-of-news-on-home "This number most 3*k-1" 5)
(def number-of-other-news-on-home "Less than 5" 3)

;; HELPERS

(defn make-link [title url]
  (enlive/do->
    (enlive/set-attr :href url)
    (enlive/content title)))

(defn links-list [posts]
  (enlive/clone-for [{:keys [title url]} posts]
    [:a] (make-link title url)))

(defn page-title [header]
   (enlive/at header [:h1] (enlive/content (:title metadata))))

;; STATIC INCLUDES

(enlive/defsnippet navigation-template (template-path "_navigation.html")
  [:li] [] identity)

(enlive/defsnippet footer-template (template-path "_footer.html")
  [enlive/root] [] identity)

(defn general-template [html]
  (enlive/at html
    [:#header :ul] (enlive/content (navigation-template))
    [:footer] (enlive/content (footer-template))
    [:#footer :ul] (enlive/content (navigation-template))
    [:head :title] (enlive/content (:title metadata))))

;; NEWS BLOCK FOR HOME

(enlive/defsnippet template-news-block-model index-template-file
 [:.post.cell]
 [{:keys [date title url description tags]}]
 [:h3 :a] (make-link title url)
 [:p.post-info] (enlive/content date)
 [:p :a] (enlive/set-attr :href url)
 [:p.tags :a] (links-list (map (fn [t] (update-in t [:title] #(str "#" %))) tags))
 [:div :div] (enlive/html-content description))

(enlive/defsnippet template-other-news index-template-file
 [:#news :> enlive/last-child :li]
 [{:keys [title url]}]
 [:a] (make-link title url))

(enlive/defsnippet template-other-news-block-model index-template-file
 [:#news  :> enlive/last-child]
 [content]
 [:.post :ul :li] (links-list (take number-of-other-news-on-home content)))

;; HELLO WORLD for HOME

;(enlive/defsnippet hello-world-template (template-path "_examples.html")
;  [enlive/root] 
;  [] 
;  [:code] identity)

;; RECENT POSTS block

(enlive/defsnippet template-recent-post base-template-file
  [:.content.standard-layout :.sidebar :.navigation]
  []
  [:h4 :strong] (enlive/content "Последние новости")
  [:ul.menu :li] (links-list (recent-posts-sidebar)))

;; ARCHIVES

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

(enlive/defsnippet template-tags-list site-template-file
  [:.content.standard-layout :.sidebar :.navigation]
  []
  [:h4 :strong] (enlive/content "Список тегов")
  [:ul.menu :li] (links-list (map (fn [t] {:title (str (:tag t) " (" (:count t) ")") :url (:url t)}) 
                                 (:categories metadata))))

