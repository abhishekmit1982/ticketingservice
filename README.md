# ticketingservice

The implementation is based on assuming the theatre seating to be as shown as below

<-----------------------Seat Number--------------->
   	  1   2   3   4   5   6   7   8   9   10
* |    1  X   X   X   X   X   X   X   X   X   X 
 
* R|   2  X   X   X   O   X   X   X   X   X   X 

* O|   3  X   X   X   X   X   X   X   X   X   X 

* W|   4  X   X   X   X   X   X   X   X   X   X 

*  |   5  X   X   O   X   X   X   X   X   X   X 

The seat assignment logic uses regular expression where X denotes a available seat
and O signifies an occupied seat.
So to find 5 available seats, a pattern matching logic of 'XXXXX' is used.
If 5 adjacent seats are not found,then match 4-1(XXXX and X), 
else match 3-2 (XXX and XX), else match 3-1-1(XXX and X and X),
else match 2-2-1(XX and XX and X),else match 2-1-1-1(XX and X and X and X), 
else match 1-1-1-1-1(X and X and X and X and X).


The seat assignment logic tries to find as many adjacent seats together as requested.
Also, the preference is given to available seats towards the center of seats.
i.e in the diagram above, the seats closest to seat number 5,6 will be chosen first for assignment.

