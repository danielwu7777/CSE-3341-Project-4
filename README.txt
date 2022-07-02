name: Daniel Wu

Files: All Non-terminal class .java files
       Executor class: includes many helper methods

Comments: All semantic checking is implemented. I added many helper methods to minimize code in execute functions. 

Description: I used a stack of stack of maps to keep track of the local scopes. 
When a new scope is entered, a new frame (stack of maps) is pushed onto the top of the stack of stacks.

Testing: I first implemented the new parse functions and printed out each test case to confirm that I was parsing correctly. I then implemented the new execute methods and added 
helper methods to the Executor class when appropriate. When I would get an error in my execution, I would heavily use System.out.println to check if my code is functioning as expected
and to trace where my bugs were.
