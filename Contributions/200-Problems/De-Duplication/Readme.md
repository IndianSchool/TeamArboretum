===========================================================================================================================================
Program to remove duplicates
===========================================================================================================================================
Program : removeDuplicates.jar
Purpose : Many of the times we would end up with cleaning the input files for the following reasons
          1. Input might have duplicates in it.
          2. Input may be invalid.
          There are even many more scenarios to be looked in while processing the input files to get the desired results.
          
          So we will talk about the first case which is having duplicates in the input.
          
          Why do we remove duplicates from the input?
          Answer  : Suppose you have a factor to be applied on the some sales done on a particular region as below
                    region1     0.5
                    region1     0.5
                    region2     0.25
                    
                    Sales :   region1   100
                              region2   200
                              
                    So after applying then the data would be as 
                              region1   50
                              region1   50
                              region2   50
                     
                    This clearly states that the data before and applying factor is same. 
                    This might even go worse as sometimes it would it double/trible making worse than ever.
===========================================================================================================================================
Logic Used  :   Mapper splits the data into key, value pairs 
                But the reducer takes the key as constant and iterate for no of values available for the same key.
===========================================================================================================================================
Pseudocode  :   Input :-  duplicates.csv

                        
                
              
                    
