import java.util.List;

/**
 * An assassination ring created by linked lists where one person is stalking another. When a person
 * is killed the link is moved to a graveyard list and removed from the assassin list
 * 
 * Java 143 Project created by the UW and completed by Justin Neff
 * 
 * @author Justin
 *
 */
public class AssassinManager {
  // YOUR CODE GOES HERE

  private AssassinNode killRingHead = null;
  private AssassinNode graveyardHead = null;

  /**
   * Takes in a List of strings to build an assassin linked list.
   * 
   * @param names is a list of assassins
   * @throws IllegalArgumentException when a null list or a list of size 0 is provided
   */
  public AssassinManager(List<String> names) {
    //makes sure the List is not empty or null
    if (names == null || names.size() == 0) {
      throw new IllegalArgumentException("The assassin list is empty");
    }
    //creates and links a new node for each name in the list
    for (String name : names) {
      killRingHead = new AssassinNode(name, killRingHead);
    }
  }

  /**
   * @return true if there is one person left
   */
  public boolean isGameOver() {
    return (killRingHead.next == null);
  }

  /**
   * @return the name of the winner
   */
  public String winner() {
    return isGameOver() ? killRingHead.name : null;
  }

  /**
   * Travels along the linked list printing out the names of the assassins in the graveyard and who
   * killed them.
   */
  public void printGraveyard() {
    //checks to see if there is anything in the graveyard
    if (graveyardHead != null) {
      //if there is then get the graveyard head node.
      AssassinNode node = graveyardHead;
      //search through all the nodes until you hit the end
      while (node != null) {
        //print the dead people and their killers
        System.out.println("    " + node.name + " was killed by " + node.killer);
        node = node.next;
      }
    }
  }

  /**
   * Travels along the linked list printing out the names of the assassins who are still alive, who
   * they are stalking, and who is stalking them.
   */
  public void printKillRing() {
    AssassinNode node = killRingHead;
    //loop through all the assassins in the ring
    while (node.next != null) {
      //print out the assassin's name and who they are stalking
      System.out.println("    " + node.name + " is stalking " + node.next.name);
      node = node.next;
    }
    //the last node will print out the head node as the person it is stalking
    System.out.println("    " + node.name + " is stalking " + killRingHead.name);
  }

  /**
   * Checks to see if an assassins name is present in the graveyard.
   * 
   * @param name assassin's name
   * @return true if the name is found in the graveyard
   */
  public boolean graveyardContains(String name) {
    return ringContains(graveyardHead, name);
  }

  /**
   * Checks to see if an assassins name is present in the kill ring.
   * 
   * @param name assassin's name
   * @return true if the name is found in the kill ring
   */
  public boolean killRingContains(String name) {
    return ringContains(killRingHead, name);
  }

  /**
   * Removes the name passed in from the kill ring and places it into the graveyard.
   * 
   * @param name person to be assassinated
   * @throws IllegalArgumentException when the name is not in the kill ring
   * @throws IllegalStateException when the game is supposed to be over already
   */
  public void kill(String name) {
    //makes sure the game is still in progress.
    if (isGameOver()) {
      throw new IllegalStateException("Game should be over.");
    }
    //makes sure the person is alive.
    if (!killRingContains(name)) {
      throw new IllegalArgumentException("This assassin was not found.");
    }

    AssassinNode node = killRingHead;
    AssassinNode gynode = graveyardHead;
    //checks the node's next name to see if it is the one that needs to be killed. If so
    //use the current nodes name as the next nodes killer.
    while (node.next != null && !node.next.name.equalsIgnoreCase(name)) {
      node = node.next;
    }
    //if you have reached the end then the head needs to be killed. We skip it the first
    //time since you would have to find the last node anyways if the first node needs to be killed.
    if (node.next == null) {
      graveyardHead = killRingHead;
      killRingHead = killRingHead.next;
    } else {
      graveyardHead = node.next;
      node.next = node.next.next;
    }

    graveyardHead.next = gynode;
    graveyardHead.killer = node.name;
  }

  //searches through the linked list for the name provided
  private boolean ringContains(AssassinNode headNode, String name) {
    if (headNode == null) {
      return false;
    }
    while (headNode.next != null && !headNode.name.equalsIgnoreCase(name)) {
      headNode = headNode.next;
    }
    //if you have exited the loop you are either at the end or you exited when you found the match.
    //now we return true if stopped because they were equal or return false if we just hit the end
    //of the list
    return headNode.name.equalsIgnoreCase(name) ? true : false;
  }

  //////// DO NOT MODIFY AssassinNode. You will lose points if you do. ////////
  /**
   * Each AssassinNode object represents a single node in a linked list for a game of Assassin.
   */
  private static class AssassinNode {
    public final String name; // this person's name
    public String killer; // name of who killed this person (null if alive)
    public AssassinNode next; // next node in the list (null if none)

    /**
     * Constructs a new node to store the given name and no next node.
     */
    public AssassinNode(String name) {
      this(name, null);
    }

    /**
     * Constructs a new node to store the given name and a reference to the given next node.
     */
    public AssassinNode(String name, AssassinNode next) {
      this.name = name;
      this.killer = null;
      this.next = next;
    }
  }
}
