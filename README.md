#Android-VK-Api library

**Need to work with vk.com in your Android app? Just use Android-VK-Api library!**

-----
####*Structure of Android-VK-Api is maximally similar to vk.com api*

All methods has same parameters as their originals on api.vk.com

###*Pavel Durov says:* 
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;«Best Android vk.com api implementation I ever seen!» (little joke)

-----


####How to use library in your project?

##You can import it in 2 ways:
* As jar library (simple, but troubles with javadoc), just take Android-VK-Api-x.x.x.jar from Jar folder
* As [Android Library Project] (http://developer.android.com/tools/projects/projects-eclipse.html#ReferencingLibraryProject),
  download Android-VK-Api folder and connect it as library project in your IDE

## Sample of code
    import com.artemzin.android.vk.api.VKApi;
    ...
    VKApi api = new VKApi("access_token");
    // That is all! Now you can work with vk.com api!
    
    // For example, lets get all friends of Pavel Durov
    ArrayList<VKUser> pavelDurovFriends = api.friends.get(1L, VKUser.ALL_FIELDS, null, null, null, null, null);
    
    
------
Some ideas were taken from the [thest1 VK SDK project] (https://github.com/thest1/Android-VKontakte-SDK), 
but Android-VK-Api has **better code and documentation** with links to [vk.com developers docs] (http://vk.com/developers.php?oid=-1&p=%D0%9E%D0%BF%D0%B8%D1%81%D0%B0%D0%BD%D0%B8%D0%B5_%D0%BC%D0%B5%D1%82%D0%BE%D0%B4%D0%BE%D0%B2_API)
, plus full code coverage with JUnit tests.

