PURDUE GREEKLIFE

This is a working version of ranking for purdue greeklife the results 
are as follows

---SORORITY RANK---
1) KKG, rank coefficient: 7.0
2) a chi o, rank coefficient: 7.0
3) Tridelt, rank coefficient: 6.333333333333333
4) pi phi, rank coefficient: 6.333333333333333
5) theta, rank coefficient: 6.333333333333333
6) ZTA, rank coefficient: 6.185185185185186
7) chi o, rank coefficient: 5.666666666666667
8) a phi, rank coefficient: 5.0
9) dg, rank coefficient: 4.333333333333333
10) dz, rank coefficient: 4.333333333333333
11) azd, rank coefficient: 3.0
12) phi mu, rank coefficient: 2.3333333333333335
13) sigma kappa, rank coefficient: 2.3333333333333335
14) aopi, rank coefficient: 1.6666666666666667
15) agd, rank coefficient: 1.0
16) g phi b, rank coefficient: 1.0

---FRATERNITY RANK---
1) agr, rank coefficient: 6.777777777777778
2) sae, rank coefficient: 6.5555555555555545
3) lambda chi, rank coefficient: 6.506172839506173
4) delts, rank coefficient: 6.506172839506173
5) kappa sig, rank coefficient: 6.333333333333333
6) snu, rank coefficient: 6.185185185185186
7) pi kapps, rank coefficient: 5.666666666666667
8) fiji, rank coefficient: 5.222222222222221
9) phi psi, rank coefficient: 5.0
10) sig chi, rank coefficient: 5.0
11) d chi, rank coefficient: 3.2222222222222228
12) zbt, rank coefficient: 3.2222222222222228
13) theta chi, rank coefficient: 2.666666666666667
14) ato, rank coefficient: 2.3333333333333335
15) pike, rank coefficient: 1.6666666666666667
16) du, rank coefficient: 1.6666666666666667
17) Farm House, rank coefficient: 1.6666666666666667
18) akl, rank coefficient: 1.0



The method used is to find the sorority without grand prix/ football pairs and
label them min sororities. The bfs is run of the min sororities and their
fraternity distance is stored. The sororities are then ranked by the average 
distance of their paired fraternities and the fraternaties are in turn 
ranked by the score of the average of their paired sororities.

The method used is susceptible to clustering issues; because there is a limited
amount of data, highly clustered tiers can skew the results.

a basic BFS from the min nodes shows fairly accurate clustering:

Min sororities:agd, g phi b, 
Fraternity: sae, fraternityDistToExit: 7
Fraternity: pi kapps, fraternityDistToExit: 5
Fraternity: lambda chi, fraternityDistToExit: 7
Fraternity: delts, fraternityDistToExit: 7
Fraternity: snu, fraternityDistToExit: 9
Fraternity: agr, fraternityDistToExit: 7
Fraternity: kappa sig, fraternityDistToExit: 7
Fraternity: fiji, fraternityDistToExit: 5
Fraternity: phi psi, fraternityDistToExit: 5
Fraternity: d chi, fraternityDistToExit: 3
Fraternity: zbt, fraternityDistToExit: 3
Fraternity: sig chi, fraternityDistToExit: 5
Fraternity: pike, fraternityDistToExit: 1
Fraternity: ato, fraternityDistToExit: 3
Fraternity: du, fraternityDistToExit: 1
Fraternity: theta chi, fraternityDistToExit: 3
Fraternity: akl, fraternityDistToExit: 1
Fraternity: Farm House, fraternityDistToExit: 3


Up next: clustering algorithms
