import java.util.EmptyStackException;
class Stack_double{
    private Node_double top; //reference to the last we added
    //constructors
    Stack_double(){top = null;}

    //operations
    public boolean isEmpty(){return top==null;}
    public double peek(){
        if(!this.isEmpty()){return top.data;}
        else{
            throw new EmptyStackException();
        }
    }
    public void push(double newElem){
        //this is equivalent to prepend, insert at the 
        //beginning of linked list
        Node_double t =new Node_double(newElem, this.top);
        top = t;
    }
    public double pop(){
        if(!this.isEmpty()){
            double tmp = this.peek();
            this.top = this.top.next;
            return tmp;
        }
        else{
          throw new EmptyStackException();
        }
    }
}
