I. Prequistes
------------
* Java 7 +
* Gradle 2.1 +

II. Running Application
------------
1. Make sure you have JAVA_HOME added to your bash profile environment path.
2. Make sure you have GRADLE_HOME addded to your bash profile environment path.
3. Checkout the project using GIT
4. git clone [url of project]
5. cd [folder name]
6. "gradle run" - in the root folder to run the application
7. "gradle test" - in the root folder to run the tests.


II. Design
----------

I tried to keep the task simple. I looked for urls that belonged to the domain. I also
stripped any urls with hashes in them. Furthermore, I stripped any external urls with the crawler.
There is a task that runs every hour to crawl the website and stores the results in a database.

I used a breadth first approach where I visited all the pages in the order I found them. However, this
could have been experimented with and maybe a more min-max algorithm approach could have been used.

IV. Trade Offs
----------

When the app first starts up and the crawler is still running we see no site map. This is problematic. The way we can
fix this is by using Promises/Futures where we use multi threads to go fetch pages instead of waiting for the whole task
to complete when the Thread finishes. Using the Async approach we would crawl the web pages much quicker and return the
results quicker without blocking.

If I had time I would have used something like Akka [akka.io] to handle the concurrency through the actor model
and use a non-blocking approach.

Other issues is web page urls with "#" can create a recursive loop because you are calling the same page each time.
I had to strip these pages out as they were a duplicate of another page with a slightly modified url with a hash to 
make it easier for the user to navigate to a particular area on the page.

When running tests on other domains I ran in to issues with canonicalization of urls. We need to revert all
urls that are found to one given form such as canonical form. Some implementation issues that I came across were
fetching same url twice which could be because of canonical url problems and not recording what urls were visited. So I
started to record what urls were visited already. Furthermore, if there is a site with a lot of urls such as newspaper
the urls will exponential grow and the crawler because un efficient and this where we need to use more threads or an
actor model with fault tolerance.

I had cases if the website was down the crawler would slow down or timeout. I also noticed redirection loops as well on
some example domains that I used! Leading on, another issue I ran into was that I was essentially getting pages
that were not of the html/text mime type. I think I was potentially downloading files. This issue was fixed by 
checking for the mime type of the pages as well. If the mime page was not html I would not process the page.
