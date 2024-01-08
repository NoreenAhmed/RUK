package kindergarten;

//import javax.lang.model.util.ElementScanner14;

/**
 * This class represents a Classroom, with:
 * - an SNode instance variable for students in line,
 * - an SNode instance variable for musical chairs, pointing to the last student in the list,
 * - a boolean array for seating availability (eg. can a student sit in a given seat), and
 * - a Student array parallel to seatingAvailability to show students filed into seats 
 * --- (more formally, seatingAvailability[i][j] also refers to the same seat in studentsSitting[i][j])
 * 
 * @author Ethan Chou
 * @author Kal Pandit
 * @author Maksims Kurjanovics Kravcenko
 */
public class Classroom {
    private SNode studentsInLine;         // when students are in line: references the FIRST student in the LL
    private SNode musicalChairs;          // when students are in musical chairs: references the LAST student in the CLL
    private boolean[][] seatingLocation;  // represents the classroom seats that are available to students
    private Student[][] studentsSitting;  // when students are sitting in the classroom: contains the students

    /**
     * Constructor for classrooms. Do not edit.
     * @param l passes in students in line
     * @param m passes in musical chairs
     * @param a passes in availability
     * @param s passes in students sitting
     */
    public Classroom ( SNode l, SNode m, boolean[][] a, Student[][] s ) {
		studentsInLine  = l;
        musicalChairs   = m;
		seatingLocation = a;
        studentsSitting = s;
	}
    /**
     * Default constructor starts an empty classroom. Do not edit.
     */
    public Classroom() {
        this(null, null, null, null);
    }

    /**
     * This method simulates students standing in line and coming into the classroom (not leaving the line).
     * 
     * It does this by reading students from input file and inserting these students studentsInLine singly linked list.
     * 
     * 1. Open the file using StdIn.setFile(filename);
     * 
     * 2. For each line of the input file:
     *     1. read a student from the file
     *     2. create an object of type Student with the student information
     *     3. insert the Student object at the FRONT of the linked list
     * 
     * Input file has:
     * 1) one line containing an integer representing the number of students in the file, say x
     * 2) x lines containing one student per line. Each line has the following student 
     * information separated by spaces: FirstName LastName Height
     * 
     * To read a string using StdIn use StdIn.readString()
     * 
     * The input file has Students in REVERSE alphabetical order. So, at the end of this
     * method all students are in line in alphabetical order.
     * 
     * DO NOT implement a sorting method, PRACTICE add to front.
     * 
     * @param filename the student information input file
     */
    public void enterClassroom ( String filename ) {
        StdIn.setFile(filename);
        int length = StdIn.readInt();
        for (int i=0; i<length; i++) { //traverses the file to make each line a student object
            String firstname =StdIn.readString();
            String lastname = StdIn.readString();
            int height = StdIn.readInt();
            Student newStudent = new Student(firstname, lastname, height); //creates Student object with the file data
            
            SNode newNode = new SNode(newStudent, studentsInLine); //creates Node 
            studentsInLine = newNode;  //adds the node to the front of the list

        }

    
    }

    /**
     * 
     * This method creates and initializes the seatingAvailability (2D array) of 
     * available seats inside the classroom. Imagine that unavailable seats are broken and cannot be used.
     *      
     * 1. Open the file using StdIn.setFile(seatingChart);
     * 
     * 2. You will read the seating chart input file with the format:
     * An integer representing the number of rows in the classroom, say r
     * An integer representing the number of columns in the classroom, say c
     * Number of r lines, each containing c true or false values (true represents that a 
     * seat is present in that column)
     * 
     * 3. Initialize seatingLocation and studentsSitting arrays with r rows and c columns
     * 
     * 4. Update seatingLocation with the boolean values read from the input file
     * 
     * This method does not seat students on the seats.
     * 
     * @param seatingChart the seating chart input file
     */
    public void setupSeats(String seatingChart) {
        StdIn.setFile(seatingChart);
        int r = StdIn.readInt(); //number of rows
        int c= StdIn.readInt(); //number of columns
        seatingLocation = new boolean[r][c]; //initializes array
        studentsSitting = new Student [r][c]; //initializes array

        for (int i=0;i<r; i++ ) {
            for (int j=0; j<c; j++) {
                seatingLocation[i][j] = StdIn.readBoolean(); //reads boolean values of array

                }
            }
        }
    
    /**
     * 
     * This method simulates students standing inline and then taking their seats in the classroom.
     * 
     * 1. Starting from the front of the studentsInLine singly linked list
     * 2. Remove one student at a time from the list and insert the student into studentsSitting according to
     *    seatingLocations
     * 
     * studentsInLine will then be empty
     * 
     * If the students just played musical chairs, the winner of musical chairs is seated separately 
     * by seatMusicalChairsWinner().
     */
    public void seatStudents () {
        
            
        for (int i=0; i<studentsSitting.length; i++) {
            for (int j=0; j<studentsSitting[i].length; j++) {
                if (studentsInLine != null) {
                    if (seatingLocation[i][j]==true) {
                        Student frontstudent = studentsInLine.getStudent();
                        studentsSitting[i][j] =frontstudent;
                        studentsInLine=studentsInLine.getNext(); //deletes front of LL
                    }
                    
                }
    
            }
        }
    }

    /**
     * Traverses studentsSitting row-wise (starting at row 0) removing a seated
     * student and adding that student to the end of the musicalChairs list.
     * 
     * row-wise: starts at index [0][0] traverses the entire first row and then moves
     * into second row.
     */
    public void insertMusicalChairs () {
        //int i=0;
        //int tempsize = 0;
        for (int i=0; i<studentsSitting.length; i++) {
           for (int j=0; j<studentsSitting[i].length; j++) {
                if (studentsSitting[i][j] != null) {
                    Student studentinchair = new Student();
                    studentinchair = studentsSitting[i][j];


                //System.out.println("the student sitting is:" + studentinchair);

                    SNode newnode = new SNode();
                    newnode.setStudent(studentinchair);
                

                    //System.out.println("newnode student is: " + newnode.getStudent());

                    if (musicalChairs == null) {

                        newnode.setNext(newnode);
                        //SNode next = newnode.getNext();
                        musicalChairs=newnode;
                        
                        
                        //System.out.println("newnode.next is " + newnode.getNext());
                    } else {
                        newnode.setNext(musicalChairs.getNext());
                        musicalChairs.setNext(newnode);
                        musicalChairs=newnode;
                       
                        
                    }
                    //tempsize +=1;

                }
                
                
                studentsSitting[i][j] = null;



               }
                
            }  
            //System.out.println("size of musical chairs is:" + tempsize);
        }
    
        
        
        // WRITE YOUR CODE HERE
        //musicalChairs.setStudent(studentinchair);
    

    /**
     * 
     * Removes a random student from the musicalChairs.
     * 
     * @param size represents the number of students currently sitting in musicalChairs
     * 
     * 1. Select a random student to be removed from the musicalChairs CLL.
     * 2. Searches for the selected student, deletes that student from musicalChairs
     * 3. Calls insertByHeight to insert the deleted student into studentsInLine list.
     * 
     * Requires the use of StdRandom.uniform(int b) to select a random student to remove,
     * where b is the number of students currently sitting in the musicalChairs.
     * 
     * The random value denotes the refers to the position of the student to be removed
     * in the musicalChairs. 0 is the first student
     */
    public void moveStudentFromChairsToLine(int size) {
        int x= size;
        int n = StdRandom.uniform(0,x);
        SNode last = musicalChairs;
        SNode front = musicalChairs.getNext();
        //musicalChairs.
        SNode ptr= front;
        SNode prev= ptr;
        Student studenttodel = null;
        if (x==2){
            if (n==x-1) {
                
                ptr=ptr.getNext();
                studenttodel=ptr.getStudent();
                
                prev.setNext(prev);
                musicalChairs=prev;

            } else { //when n=0
                studenttodel=ptr.getStudent();
                musicalChairs.setNext(musicalChairs);
                

            }
        } else {
            if (n== 0) { //if deleting from the front of CLL
                //System.out.println("deleted from front");

                studenttodel = front.getStudent();
                last.setNext(front.getNext());
                front=last.getNext();
                
            } 
                else if (n== x-1) { //delete from end of list
                    //System.out.println("deleted from end");

                    for (int i=0; i<n-1; i++) { //sets front and prev until the position
                            ptr=ptr.getNext(); //ptr is the position before the target
                    }
                    studenttodel=ptr.getNext().getStudent();
                    ptr.setNext(front);
                    musicalChairs=ptr;
                    
                
                }
       
            else {
                //System.out.println("deleted from middle");

                for (int i=0; i<n-1; i++) { //if deleting from middle of CLL
                        prev=prev.getNext();
                                                //sets the first item in list to the following
                                                            //is moving the pointer through the list- 
                                                            // stops 1 before the nth position
                        
                }
                ptr=prev.getNext();
                studenttodel = ptr.getStudent(); //the nth student is the one after front
                prev.setNext(ptr.getNext()); //sets the node pointing to the second node
            }
         }
        x --;
        //musicalChairs.setSize(x);
        //System.out.println("size of musicalChairs is: " + x );
       // System.out.println("here is deleted student" + studenttodel.getFirstName());
       insertByHeight(studenttodel);
        //   
    
    }
    /**
     * Inserts a single student, eliminated from musical chairs, to the studentsInLine list.
     * The student is inserted in ascending order by height (shortest to tallest).
     * 
     * @param studentToInsert the student eliminated from chairs to be inserted into studentsInLine
     */
    public void insertByHeight(Student studentToInsert) {
        int comp= studentToInsert.getHeight();
        SNode ptr = studentsInLine;
        SNode prev = null;
        //System.out.println("insertbyheight ptr is:" + ptr);
        //System.out.println("insertbyheight comp is:" + comp);

        if (studentsInLine==null) { //if line is empty
            SNode newnode = new SNode(studentToInsert, studentsInLine );
            studentsInLine = newnode; 
            //ptr=newnode;

        } 

        else  {// line is not empty
            //System.out.println("studentsinline is not null, entering code to insert");
            //SNode ptr = studentsInLine;
            //Student tempStudent = ptr.getStudent();
            //System.out.println( "Comp is "+ comp +" student Ht is "+tempStudent.getHeight());
            if  ( comp < ptr.getStudent().getHeight() ) { //if comp student is less than the first node, adds to front
                SNode newnode = new SNode(studentToInsert, ptr);
                newnode.setNext(ptr); //inserts as the new front 
                ptr=newnode;
                studentsInLine=ptr;
               // System.out.println("added to front");
                return;
                } 

            //else  /* if( comp < ptr.getStudent().getHeight() ) { //if comp is greater than the first node, adds to end
               // SNode newnode = new SNode(studentToInsert, studentsInLine.getNext());
                //studentsInLine.setNext(newnode);
                //return; 
        
        
            else {
                
                while (ptr.getNext() !=null) {
                    prev=ptr;
                    ptr=ptr.getNext();
                     //move to the next node 
                    if ( comp < ptr.getStudent().getHeight() ) { 
                        SNode newnode = new SNode(studentToInsert, ptr);
                        prev.setNext(newnode);
                        newnode.setNext(ptr);
                       // System.out.println("added to middle");
                        return;

                    } 
                    if (ptr.getStudent().getHeight() == comp) {
                        while (ptr.getStudent().getHeight() == comp) {
                            prev=ptr;
                            ptr=ptr.getNext();
                        }
                       SNode newnode = new SNode (studentToInsert, ptr);
                       prev.setNext(newnode);
                       //System.out.println("ADDED HT W SAME VAL");
                       return;
                    }
                    //studentsInLine.setNext(newnode);
                 
                //if (studentsInLine.getStudent().getHeight() > compheight) {
    
                //}
               // prev=ptr;
                //ptr=ptr.getNext();
                //else all- add to end 
                
              
                }
                SNode newnode = new SNode (studentToInsert, null);
                ptr.setNext(newnode);
                //System.out.println("added to end");
                /*
                return; */
                
            }
         }
        
        
        
    
    }    

    /**
     * 
     * Removes eliminated students from the musicalChairs and inserts those students 
     * back in studentsInLine in ascending height order (shortest to tallest).
     * 
     * At the end of this method, all students will be in studentsInLine besides 
     * the winner, who will be in musicalChairs alone.
     * 
     * 1. Find the number of students currently on musicalChairs
     * 2. While there are more than 1 student in musicalChairs, call moveRandomStudentFromChairsToLine()
     */
    public void eliminateLosingStudents() {
        //System.out.println(" entering the method : elimnated Losing students");
        //musicalChairs.;
       
        
        //musicalChairs.setSize(2);

        //System.out.println("size of musical chairs is:" + musicalChairs.getSize());
         //default size set to 0
         int size =0;
        if (musicalChairs !=null) {
            size =1;
            SNode front = musicalChairs.getNext();
        
            SNode ptr = front.getNext();
            while (ptr.getStudent()!=front.getStudent()) {
                size++;
                ptr=ptr.getNext();
            } 
        } else {
            size =0;
        }
            
       
        
       // printMusicalChairs();
  
       //for (int i=newsize; i>=0; i--) {
        for (int i=size; i>1 ; i--) {
            moveStudentFromChairsToLine(i);
         }
            
            //newsize--;
            //moveStudentFromChairsToLine(newsize);

       
        //}
            
        
        //System.out.println("here is remaining musicalChairs" + );
        // WRITE YOUR CODE HERE
    }

    /* 
     * If musicalChairs (circular linked list) contains ONLY ONE student (the winner), 
     * this method removes the winner from musicalChairs and inserts that student 
     * into the first available seat in studentsSitting. musicChairs will then be empty.
     * 
     * This only happens when the musical chairs was just played.
     * 
     * This methods does nothing if there is more than one student in musicalChairs 
     * or if musicalChairs is empty.
     */
    public void seatMusicalChairsWinner () {
        //int size =0;
        //int size = musicalChairs.getSize();
        int size =0;
        if (musicalChairs !=null) {
            size =1;
            SNode front = musicalChairs.getNext();
        
            SNode ptr = front.getNext();
            while (ptr.getStudent()!=front.getStudent()) {
                size++;
                ptr=ptr.getNext();
            } 
        } else {
            size =0;
        }

        if (size==1) {
        for (int i=0; i<studentsSitting.length; i++) {
            for (int j=0; j<studentsSitting[i].length; j++) {
                if (seatingLocation[i][j]==true) {
                    Student studenttoinsert= musicalChairs.getStudent();
                    studentsSitting[i][j] =studenttoinsert;
                    seatingLocation[i][j] = false;
                    musicalChairs = null;
                    return;
                    }
                }
            }
        
        }                
            
    
    }

    /**
     * 
     * This method represents the game of musical chairs!
     * 
     * This method calls previously written methods to repeatedly remove students from 
     * the musicalChairs until there is only one student (the winner), seats the winner
     * and seat the students from the studentsInline.
     * 
     * *****DO NOT****** UPDATE THIS METHOD
     */
    public void playMusicalChairs() {

	/* DO NOT UPDATE THIS METHOD */
        eliminateLosingStudents();
        seatMusicalChairsWinner();
        seatStudents();
    } 

    /**
     * Used by driver to display students in line
     * DO NOT edit.
     */
    public void printStudentsInLine () {

        //Print studentsInLine
        StdOut.println ( "Students in Line:" );
        if ( studentsInLine == null ) { StdOut.println("EMPTY"); }

        for ( SNode ptr = studentsInLine; ptr != null; ptr = ptr.getNext() ) {
            StdOut.print ( ptr.getStudent().print() );
            if ( ptr.getNext() != null ) { StdOut.print ( " -> " ); }
        }
        StdOut.println();
        StdOut.println();
    }

    /**
     * Prints the seated students; can use this method to debug.
     * DO NOT edit.
     */
    public void printSeatedStudents () {

        StdOut.println("Sitting Students:");

        if ( studentsSitting != null ) {
        
            for ( int i = 0; i < studentsSitting.length; i++ ) {
                for ( int j = 0; j < studentsSitting[i].length; j++ ) {

                    String stringToPrint = "";
                    if ( studentsSitting[i][j] == null ) {

                        if (seatingLocation[i][j] == false) {stringToPrint = "X";}
                        else { stringToPrint = "EMPTY"; }

                    } else { stringToPrint = studentsSitting[i][j].print();}

                    StdOut.print ( stringToPrint );
                    
                    for ( int o = 0; o < (10 - stringToPrint.length()); o++ ) {
                        StdOut.print (" ");
                    }
                }
                StdOut.println();
            }
        } else {
            StdOut.println("EMPTY");
        }
        StdOut.println();
    }

    /**
     * Prints the musical chairs; can use this method to debug.
     * DO NOT edit.
     */
    public void printMusicalChairs () {
        StdOut.println ( "Students in Musical Chairs:" );

        if ( musicalChairs == null ) {
            StdOut.println("EMPTY");
            StdOut.println();
            return;
        }
        SNode ptr;
        for ( ptr = musicalChairs.getNext(); ptr != musicalChairs; ptr = ptr.getNext() ) {
            StdOut.print(ptr.getStudent().print() + " -> ");
        }
        if ( ptr == musicalChairs) {
            StdOut.print(musicalChairs.getStudent().print() + " - POINTS TO FRONT");
        }
        StdOut.println();
    }

    /**
     * Prints the state of the classroom; can use this method to debug.
     * DO NOT edit.
     */
    public void printClassroom() {
        printStudentsInLine();
        printSeatedStudents();
        printMusicalChairs();
    }

    /**
     * Used to get and set objects.
     * DO NOT edit.
     */

    public SNode getStudentsInLine() { return studentsInLine; }
    public void setStudentsInLine(SNode l) { studentsInLine = l; }

    public SNode getMusicalChairs() { return musicalChairs; }
    public void setMusicalChairs(SNode m) { musicalChairs = m; }

    public boolean[][] getSeatingLocation() { return seatingLocation; }
    public void setSeatingLocation(boolean[][] a) { seatingLocation = a; }

    public Student[][] getStudentsSitting() { return studentsSitting; }
    public void setStudentsSitting(Student[][] s) { studentsSitting = s; }

}
