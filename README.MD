I. Prequistes
------------
Java 7 +
Gradle 2.1 +

II. Running Application
------------
Make sure you have JAVA_HOME added to your bash profile environment path.
Make sure you have GRADLE_HOME addded to your bash profile environment path.
Checkout the project using GIT
git clone [url of project]
cd [folder name]
"gradle run" - in the root folder to run the application
"gradle test" - in the root folder to run the tests.


II. Design
----------

I tried to keep the task simple. I looked for urls that belonged to the domain. I also
stripped any urls with hashes in them. Furthermore I stripped any external urls with the crawler.
There is a task that runs every hour to crawl the website and stores the results in a database.

I used a breadth first approach where I visited all the pages in the order I found them. However this
could have been experimented with and maybe a more min-max algorithm approach could have been used.

IV. Trade Offs
----------

When the app first starts up and the crawler is still running we see no sitemap. This is problematic. The way we can 
fix this is by using Promises/Futures where we use multi threads to go fetch pages instead of thread. 
This way the crawler will crawl the webpages much quicker and return the results quicker.

If I had time I would have used something like Akka [akka.io] to handle the concurrency through the actor model.

Other issues is webpages with "#" can create a recursive loop because you are calling the same page each time.
I had to strip these pages out as they were a duplicate of another page with a slightly modified url with a hash to 
make it easier for the user to navigate to a particular area on the page.

When running tests on other domains I ran in to issues with canonicalization of urls. We need to revert all
urls that are found to one given form such as canonical form. Some implementation issues that I came across were
fetching same url twice which could be because of canonical url problems and not recording what urls were visited. So I
started to record what urls were visited already. Furthermore, if there is a site with a lot of urls such as newspaper
the urls will exponential grow and the crawler because un efficient and this where we need to use more threads or an
actor model with fault tolerance.

I had cases if the website was down the crawler would slow down or timeout or notice redirection loops as well!
Another issue I ran into I started was I was getting non HTML files being sent. I had to check for mime type of the 
pages as well. If the mime page was not html I would not process the page.