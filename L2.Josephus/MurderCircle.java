/*

+ STUDENT NAME :: Nicholas Sims

+ STUDENT ID   :: 80713446

================================================================================

  MurderCircle

================================================================================

A rather grizzly example, yeah?

1. Establish the Murder Circle, where each person in the circle murders the kth
person adjacent them in a single direction.

2. Populate the circle, bind the last person's adjacency to the first person.

-- Conceptually the person killing their immediate adjacent is referred to as a
"sword", a survivor is marked as the last sword that performed the killing.

-- To reduce the requirement for input validation, the first sword is calculated
starting from the last person to enter the circle rather than the first. This
means that if each kth person is stabbed to death, and k = 2, then the first to
enter the circle is the first to murder their compatriot.

3. Calculate the kth person from the most recent sword, and remove the adjacent
person from circle. The GC will free the memory from any nodes released this way
since no references to them remain. Freeing .next reference isn't necessary
as the GC is smart enough to de-allocate.

4. Mark the most recent survivor and begin calculating the next sword from their position.

5. Repeat until everyone is dead

*/

public class MurderCircle {
    int id;
    MurderCircle next;
    public MurderCircle(int people) {             // Populate the circle
        this.id = 1;                              // Take a ticket in entrance
        MurderCircle walk = this;                 //
        for (int i=1;i<people;++i) {              //
            walk.next = new MurderCircle();       //
            walk.next.id = i+1;                   //
            walk = walk.next;                     //
        }                                         //
        walk.next = this;                         // Close the circle
    }
    public MurderCircle() {}                      // Empty constructor for all participants but the first

    public String beginTheKilling(int step) {                       //
        MurderCircle walk = this;                                   //
        MurderCircle sword = this;                                  // Rotate the sword to the end
        for (;sword.next!=walk;)                                    // Lazy method to avoid creating a
            sword = sword.next;                                     // doubly linked list since this isn't used
                                                                    // anywhere but here.
        System.out.printf("First: %d\n",                            //
                          sword.next.id);                           // Mark the first participant,
                                                                    // should always be 1.
        for (;;) {                                                  //
            for (int i=1;i<step;++i)                                // Locate the next sword based on stepping range.
                sword = sword.next;                                 //
            if (sword==sword.next)                                  // The last survivor escapes if they are the only sword.
                return String.valueOf(sword.id);                    //
            System.out.printf("Sword: %02d --> Kills: %02d\n",      //
                              sword.id, sword.next.id);             //
            walk = sword.next;                                      // Mark the next survivor,
            sword.next = sword.next.next;                           // Kill the person adjacent the marked survivor,
            sword = walk;                                           // Reset the sword to begin calculating the next one.
        }                                                           //
    }                                                               //

    public String toString() {
        MurderCircle root = this;
        MurderCircle walk = this.next;
        String s = String.valueOf(root.id).concat(" ");
        for (;root!=walk;) {
            s = s.concat(String.valueOf(walk.id)).concat(" ");
            walk = walk.next;
        }
        return s;
    }

    public static void main(String [] argv) {
        int n = Integer.parseInt(argv[0]);
        int k = Integer.parseInt(argv[1]);
        MurderCircle circle = new MurderCircle(n);
        System.out.printf("Survivor: %s\n", circle.beginTheKilling(k));
    }
}
