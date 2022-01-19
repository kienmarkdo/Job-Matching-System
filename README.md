# Job-Matching-System
This job matching system matches employers and applicants together in a job search. This rank/match system is implemented using the [Gale-Shapley algorithm](https://en.wikipedia.org/wiki/Gale%E2%80%93Shapley_algorithm) that guarantees that every employer and applicant will obtain the optimal choice based on their employer/applicant rankings.

## What problem does this system solve?
Suppose we have 3 employers each looking to hire 1 new employee, and 3 employees each looking to join 1 company. The names of the 3 companies are Thales, Canada Post, and Cisco; and the names of the 3 applicants are Olivia, Jackson, and Sophia. Each company ranks the applicant they want to hire from best to worst; likewise, each applicant ranks the company they want to be in from best to worst.

Two ranking lists are provided (0 is best, 2 is worst).
  - **Preference of employers**
  
    ```diff
    0. Thales: 0. Olivia, 1. Jackson, 2. Sophia
    1. Canada Post: 2. Sophia, 1. Jackson, 0. Olivia
    2. Cisco: 0. Olivia, 2. Sophia, 1. Jackson
    ```
    
  - **Preference of applicants**
    ```
    0. Olivia: 0. Thales, 1.Canada Post, 2.Cisco
    1. Jackson: 0. Thales, 1. Canada Post, 2. Cisco
    2. Sophia: 2. Cisco, 0. Thales, 1. Canada Post
    ```

This means that Thales wants Olivia first. If they can't get Olivia, their next best option is Jackson, and lastly, their worst option is Sophia is they cannot hire Olivia and Jackson. The same idea applies to the other companies and students. We must match the employers and students in a way that is optimal for everyone. In this case, the best matching would be:

  ```
  Thales - Olivia 
  Canada Post - Jackson 
  Cisco - Sophia
  ```

This matching result is stable as there is no other matching that would be fair for every company and applicant. Let's take a look at how it works.

## How it works
There will be two lists: Employer Preferences and Applicant Preferences. The number of employers must be the same as the number of applicants. For example,
  - If there are 100 companies looking to hire new employees, a list of 100 applicants must also be provided.
  - A company will rank all of the applicants from best to worst, and vice versa.
  - Once all rankings are made, the data will be processed and the application will produce an output with all the stable employer-applicant matchings.
Similar to the [Stable Marriage problem](https://en.wikipedia.org/wiki/Stable_marriage_problem), this employer-applicant ranking system is implemented using the [Gale-Shapley algorithm](https://en.wikipedia.org/wiki/Gale%E2%80%93Shapley_algorithm) to optimally produce the results.

Example data sets are provided as well as output files that shows the optional matching to all employers and applicants.
<!--
```diff
- text in red
+ text in green
! text in orange
# text in gray
@@ text in purple (and bold)@@
``` -->
