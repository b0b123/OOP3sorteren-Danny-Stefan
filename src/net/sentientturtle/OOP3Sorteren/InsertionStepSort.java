package net.sentientturtle.OOP3Sorteren;


/**
 * Created by stefa on 8-3-2017.
 */
public class InsertionStepSort<E extends Comparable<E>> extends StepSort<E> {
    public int index;
    private boolean isDone;




    public InsertionStepSort(E[] data) {super(data);}

    public InsertionStepSort(){super (null);}

    @Override
    protected void reset(){
        index = 0;
        isDone = false;
    }


    @Override
    public boolean step() throws IllegalStateException{


        if(isDone) throw new IllegalStateException("Sort is already completed");
        if(data == null) throw new IllegalStateException("Data is null!");
        if(data.length == 1) return(isDone = true);

        int k;
        E currentElement = data[index];
        for(k = index -1; k>=0 && currentElement.compareTo(data[k])<0; k--){
            data[k+1]=data[k];
        }
        data[k+1] = currentElement;

        if (index <data.length ){
//            isDone = true;
//            return true;
            index++;
            return false;
        }
        else{

            return true;
        }



        }


    @Override
    public boolean isDone(){return isDone;}

}
