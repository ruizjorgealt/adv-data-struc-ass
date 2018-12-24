// Jorge A. Ruiz
// Comp 282
// Project 4
// April 3, 2018

import java.util.*;

class AVLNode<E>{
   E item;   
   AVLNode<E> left;
   AVLNode<E> right;
   AVLNode<E> parent;
   
   int height;
  
   public AVLNode(E x){
      item = x; 
      left = null; 
      right = null; 
      parent = null;
      height = 0;
   }
   
   public AVLNode(E x, AVLNode<E> left, AVLNode<E> right, AVLNode<E> parent){
      item = x; 
      this.left = left; this.right = right; this.parent = parent;   
      
      height =0;
   }
   
   public String toString(){
      return "(i" + ":" + item +"h" + ":" + height + ")";
   }
}

/*----------------class AVLTree ---------------------------*/
public class AVLTree<E extends Comparable<E>>{

   private AVLNode<E> root;
   private int size;
  
   public AVLTree(){  
      root = null;  
      size = 0;  
   }
   
   /*---------------- public operations --------------------*/
   
   /* Public member method that calls a private helper method*/
   
   public boolean isHeightBalanced(){
      return isHeightBalanced(root);
   }
   
   /* METHOD CALCULATES THE HEIGHT OF THE TREE*/
   
   public int TreeHeight(AVLNode<E> treeVar){
      if(treeVar == null){
   	   return -1;
	   } else{
   	   return 1 + Math.max(TreeHeight(treeVar.left), TreeHeight(treeVar.right)) ;
	   }
   }
   
   /* PERFORMS UPDATION OF THE HEIGHT*/
   
   public int HeightUpdate(AVLNode<E> t){
      int GreatHeight = 0;
      int LowHeight = 0;
      
      if(t.left != null){
         AVLNode<E> tmpLowAVLNode =t.left;
         LowHeight = tmpLowAVLNode.height;
      }
      
      if(t.right != null){
         AVLNode<E> tmpHighAVLNode = t.right;
         GreatHeight = tmpHighAVLNode.height;
       }

      if(LowHeight > GreatHeight){
         t.height = LowHeight + 1;
      } else{
         t.height = GreatHeight + 1;
      }
      
      return t.height;
   }
   
   public int getHeight(){
	   return TreeHeight(this.root);
   }
   
   /*************************************************/
   /* RECURSIVE HELPER FUNCTION TO FIND MAXIMUM NODE*/
   /*************************************************/
        
   public E MaximumNode(AVLNode<E> treeVar){
	   /* If right child is not empty*/
	   if(treeVar.right != null){
         return MaximumNode(treeVar.right); 
	   }
      
      /* Returns the maximum item*/
      return treeVar.item;
   }
   
   public E findMax(){
	   return MaximumNode(root);
   }
   
   public E removeMax(){
      /* CREATE TEMPORARY NODE*/
      E tmp = findMax();
      
      remove(tmp);
      
      return tmp;
   }
   
   /*************************************************/
   /* RECURSIVE HELPER FUNCTION TO FIND MINIMUM NODE*/
   /*************************************************/
   
   public E MinimumNode(AVLNode<E> treeVar){
      
	   /* If left child is NOT empty*/
	   if(treeVar.left != null){
         return MinimumNode(treeVar.left);
	   }
      
      /* Returns the minimum item*/
      return treeVar.item;
   }
   
   public E findMin(){
	   return MinimumNode(root);
   }
   
   public E removeMin() {
      /* CREATE TEMPORARY NODE*/
      E tmp = findMin();
      
      remove(tmp);
      
      return tmp;
   }
   
   public int getSize(){  
      return size;
   }
   
        
   public boolean find(E x){
      if(find(x,root) == null)
         return false;
      else
         return true;
   }
    
   
   public void preOrderTraversal(){
      preOrder(root);
      System.out.println();
   }
   
   public void inOrderTraversal(){
      inOrder(root);
      System.out.println();
   }
   
      
   public boolean insert(E x){
      if(root == null){
         root = new AVLNode(x, null, null, root);
         size++;
         return true;
      }    
       
      AVLNode<E> parent = null;
      AVLNode<E>  p = root;
      
      while (p != null){
         if(x.compareTo(p.item) < 0){
            parent = p; p = p.left;
         }else if ( x.compareTo(p.item) > 0){
            parent = p; p = p.right;
         }else  // duplicate value
            return false;  
      }
      
      //attach new node to parent
      AVLNode<E> insertedNode = new AVLNode<E>(x, null, null, parent);
      if(x.compareTo(parent.item) < 0)
         parent.left = insertedNode;
      else
         parent.right = insertedNode;
      size++; 
      
      /* RESTRUCTURES THE AVL TREE AFTER INSERTION*/
      
      while(insertedNode != null){
         
         /* UPDATES AND RE-CALCULATES HEIGHT AFTER INSERTION*/
    	  	insertedNode.height = HeightUpdate(insertedNode);
         
         /* CALLS RESTRUCTURE*/
         restructure(insertedNode);      
            
         /* PERFORMS AN ANCESTOR WALK UP THE BRANCH OF THE AVL TREE*/
   	  	insertedNode = insertedNode.parent;
      }
      
      return true;   
   }  //insert
   
   
   public boolean remove(E x){
      if(root == null)
         return false;  //x is not in tree
      
      //find x
      AVLNode<E> p = find(x, root);
      AVLNode<E> q = p.parent;
      if( p == null)
         return false;  //x not in tree
                  
      //Case: p has a right child child and no left child
      if( p.left == null && p.right != null) {
         deleteNodeWithOnlyRightChild(p);
         //
        
         TreeHeight(q);
      }    
       //Case: p has a left child and has no right child
      else if( p.left !=null && p.right == null) {
         deleteNodeWithOnlyLeftChild(p);
         TreeHeight(q);
      }  
            //case: p has no children
      else if (p.left ==null && p.right == null) {
    	  	deleteLeaf(p);
   	  	TreeHeight(q);
      } else{ //case : p has two children. Delete successor node
         AVLNode<E> succ =  getSuccessorNode(p);;
         p.item = succ.item;
          //delete succ node
         if(succ.right == null) {
        	 	deleteLeaf(succ);
         } else{
        	 	deleteNodeWithOnlyRightChild(succ);
         }
      }
     
      /* RESTRUCTURES THE AVL TREE AFTER DELETION*/
      while(q != null ){
         
         /* UPDATES AND RE-CALCULATES HEIGHT AFTER DELETION*/
         q.height = HeightUpdate(q);
         
         /* CALLS RESTRUCTURE*/
         restructure(q);
         
         /* PERFORMS AN ANCESTOR WALK*/
         q = q.parent;
      }
     
      return true;         
   }   //remove
 
  /********************private methods ******************************/
  
   /* METHOD CHECKS IF THE AVL TREE IS BALANCED FROM A PARTICULAR NODE*/

   private boolean isHeightBalanced(AVLNode<E> t){
      
      if(t == null){
         return true;
      }
      
      boolean leftFlag, rightFlag;
      leftFlag = isHeightBalanced(t.left);
      rightFlag = isHeightBalanced(t.right);
      
      int tmpHeight;
      tmpHeight = Math.abs(TreeHeight(t.left) - TreeHeight(t.right));
                  
      if(tmpHeight <= 1 && leftFlag && rightFlag){ 
         return true;
      }
         return false;
   }
      

   private AVLNode<E> find(E x, AVLNode<E> t){
      AVLNode<E> p = t;
      while ( p != null){
         if( x.compareTo(p.item) <0)
            p = p.left;
         else if (x.compareTo(p.item) > 0)
            p = p.right;
         else  //found x
            return p;
      }
      return null;  //x is not found
   }
   

   /*************************************/
   /* RESTRUCTURE PROPERTIES OF AVL TREE*/
   /*************************************/
          
   private AVLNode<E> restructure(AVLNode<E> t){
          	   
      if(BalanceFactor(t) == -2 || BalanceFactor(t) == 2){
         if(BalanceFactor(t) == -2){
            int tmpLow, tmpHigh;
            AVLNode<E> tmpLeft, tmpRight;
            
            tmpLeft =  t.left.left;
            tmpRight =  t.left.right;

            if(tmpLeft != null){
               tmpLow = tmpLeft.height;
            } else{
            	tmpLow = 0;
            }
                        
            if(tmpRight != null){
            	tmpHigh = tmpRight.height;
            } else{
            	tmpHigh = 0;
            }
                           
            if(tmpLow >= tmpHigh){
               RightRotation(t);
               t.height = HeightUpdate(t);
               if (t.parent != null){
                  t.parent.height = HeightUpdate(t.parent);
               }
            } else{
               LeftRotation(t.left);
               RightRotation(t);

               AVLNode<E> tmpParent = t.parent;
               if(t.parent.left != null){
                  tmpParent.left.height = HeightUpdate(t.parent.left);
               }
               if(t.parent.right != null){
                  tmpParent.right.height = HeightUpdate(t.parent.right);
               }
               
               tmpParent.height = HeightUpdate(t.parent);
            }
            
      } else if(BalanceFactor(t) == 2){
      	int tmpHigh, tmpLow;
         AVLNode<E> tmpRight = t.right.right;
         AVLNode<E> tmpLeft =  t.right.left;
               
         if(tmpRight != null){
         	tmpHigh = tmpRight.height;
         } else{
         	tmpHigh = 0;
         }
         
         if(tmpLeft != null){
         	tmpLow = tmpLeft.height;
         } else{
         	tmpLow = 0;
         }
            
         if(tmpHigh >= tmpLow) {
            
            LeftRotation(t);
            t.height = HeightUpdate(t);
            
            if (t.parent != null){
               t.parent.height = HeightUpdate(t.parent);
            }
            
         } else {
            RightRotation(t.right);
            LeftRotation(t);

            AVLNode<E> tmpParent = t.parent;
            
            if (t.parent.left != null){
               tmpParent.left.height = HeightUpdate(t.parent.left);
            }
            
            if (t.parent.right != null){
               tmpParent.right.height = HeightUpdate(t.parent.right);
            }
            
            tmpParent.height = HeightUpdate(t.parent);
         }
      }
      
	   }else if(BalanceFactor(t) > 1 || BalanceFactor(t) < -1){
   
         AVLNode<E> tmpSubt = null;
                  
         if(BalanceFactor(t) < 0){
            tmpSubt = t.left;
            if(BalanceFactor(t.left) < 0){
               RightRotation(t);
            } else{ 
                LeftRotation(t.left);
                RightRotation(t);
            }
         } else{
            tmpSubt = t.right;
            if(BalanceFactor(t.right) < 0){
                RightRotation(t.right);
                LeftRotation(t);
            } else{
                LeftRotation(t);
            }
         }
         
         tmpSubt.height = HeightUpdate(tmpSubt);
         t.height = HeightUpdate(t);
      }
      
      return t;
   }
   
   /* PERFORMS A RIGHT ROTATION SWAPPING OPERATION*/

   private AVLNode<E> RightRotation(AVLNode<E> t){
	   
	   AVLNode<E> tmpLow, tmpHigh, tmpParent;

      tmpLow = t.left;
	   tmpHigh = tmpLow.right;
	   tmpParent = t.parent;
      
      tmpLow.right = t;
      t.parent = tmpLow;
      t.left = tmpHigh;

      if(tmpHigh != null){
         tmpHigh.parent = t;
      }

      if(tmpParent!=null){
         
         if (t == tmpParent.left){
            tmpParent.left = tmpLow;
         } else{
            tmpParent.right = tmpLow;
         }
         
         tmpLow.parent = tmpParent;
         
      } else{
         root = tmpLow;
         root.parent = null;
      }
      
	   return t;
   }
   
   /* PERFORMS A LEFT ROTATION SWAPPING OPERATION*/
   
   private AVLNode<E> LeftRotation(AVLNode<E> t){ 

	   AVLNode<E> tmpParent, tmpHigh, tmpLow; 

      tmpParent = t.parent;
	   tmpHigh = t.right;
	   tmpLow = tmpHigh.left;
      tmpHigh.left = t;
      t.parent = tmpHigh;
      t.right = tmpLow;

      if(tmpLow != null){
         tmpLow.parent = t;
      }

      if(tmpParent!=null){
         if(t == tmpParent.left){
            tmpParent.left = tmpHigh;
         } else{
            tmpParent.right = tmpHigh;
         }
         
         tmpHigh.parent = tmpParent;
         
      } else{
         root = tmpHigh;
         root.parent = null;
      }
      
      return t;
   }
   
   /* CALCULATES THE BALANCING FACTOR OF SUBTREES*/
   
   private int BalanceFactor(AVLNode<E> t){
	   int LowHeight = 0;
      int GreatHeight = 0;
      int difference;
      
      if (t.left != null) {
         LowHeight = t.left.height;;
      }
         
      if (t.right != null) {
         GreatHeight = t.right.height;
      }
       
      difference = GreatHeight - LowHeight;
      
      return difference;
   }
   
   
   /***************** private remove helper methods ***************************************/
   
   private void deleteLeaf( AVLNode<E> t){
      if ( t == root)
         root = null;
      else{
         AVLNode<E>  parent = t.parent;
         if( t.item.compareTo(parent.item) < 0)
            parent.left = null;
         else
            parent.right = null;
      }
      size--;
   }
    
   private void deleteNodeWithOnlyLeftChild(AVLNode<E> t){
      if( t == root){
         root = t.left; root.parent = null; //WAS WRONG t.left.parent = root;
      } else{
         AVLNode<E> parent = t.parent;
         if( t.item.compareTo( parent.item)< 0){
            parent.left = t.left;
            t.left.parent = parent;
         } else{
            parent.right = t.left;
            t.left.parent = parent;           
         }
      }
      size--;  
   }                  
     
   private void deleteNodeWithOnlyRightChild(AVLNode<E> t){
      if( t == root){
         root = t.right; root.parent = null; // WAS WRONG t.right.parent = root;
      } else{
         AVLNode<E> parent = t.parent;
         if( t.item.compareTo(parent.item) < 0){
            parent.left = t.right;
            t.right.parent = parent;
         } else{
            parent.right = t.right;
            t.right.parent = parent;           
         }
      }
      size--;
              
   }                  

   private AVLNode<E> getSuccessorNode(AVLNode<E> t){
     //only called when t.right != null
      AVLNode<E> parent = t;
      AVLNode<E> p = t.right;
      while (p.left != null){
         parent = p; p = p.left; 
      }
      return p;
   }
                    
   //private traversal methods      
           
   private void preOrder(AVLNode<E> t){
      if ( t != null){
         System.out.print(t + " ");
         preOrder(t.left);
         preOrder(t.right);
      }
   }
     
   private void inOrder(AVLNode<E> t){
      if ( t != null){
         inOrder(t.left);
         System.out.print(t + " " );
         inOrder(t.right);
      }
   }
}