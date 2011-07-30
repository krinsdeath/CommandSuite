CommandSuite
============
Description
-----------
*CommandSuite* is a complete rewrite of all of the vanilla minecraft commands.

*   by **krinsdeath**

>   This software is released under the *don't be a douche* license. You may
>   copy or modify this software in any way you see fit, but must reference
>   me (*krinsdeath*) in the source code as an original author. This code may
>   not be distributed for commercial or monetary gain, unless with my express
>   permission.

Features
--------
*   Extends the functionality of the original minecraft commands.
*   */give* allows you to specify names of items (which are customizable through
    items.yml), including damage values with **name**:*data*
*   */time* accepts many parameters, including *set*, *add*, *day*, *night*, *dusk*,
    as well as no parameters at all (which defaults to */time day*)
*   Administrative commands like */ban* and */unban* have been extended, and allow
    flags to determine their behavior.
    example: */ban SomePlayer -i Stop spamming!*
    Will ban SomePlayer's IP address and kick them with the message "Stop spamming!"
*   Teleportation commands have been expanded, and now include */tp*, */tpto*, */tphere*
    (or */bring*), and */tpall* (or */bringall*)
*   */list* will now show SomePlayer (WorldName), SomePlayer2 (WorldName2)
*   */who player* will show the location of the player specified, or your own location
    For admins, this will also show their IP address.