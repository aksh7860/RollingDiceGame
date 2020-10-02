import java.util.*;
class RollingDice {
	static class PlayerInfo {
		String playerName;
		Integer score;
		Integer oneCount;
		Boolean isGameFinished;
		PlayerInfo(String playerName,Integer score) {
			this.playerName = playerName;
			this.score = score;
			this.oneCount = 0;
			this.isGameFinished = false;
		}
	}
	public static void main(String[] args) {
		int noPlayers = Integer.parseInt(args[0]);
		int winingPoints = Integer.parseInt(args[1]);
		Scanner scanner = new Scanner(System.in);

		// Settting The Game 
		System.out.println("No of players is="+noPlayers+" winingPoints="+winingPoints);
		String readString;
		HashMap<Integer,PlayerInfo> playerInfoMap = new HashMap<Integer,PlayerInfo>();
		System.out.println("No of Players playing are"+args[0]);
		List<Integer> playingOrder = getPlayingOrder(noPlayers);
		List<Integer> currRankOrder = new ArrayList<Integer>();

		//Initalising Map and Rank
		for(int itr=0;itr<noPlayers;itr++) {
			playerInfoMap.put(itr,new PlayerInfo("Player-"+(itr+1),0));
			currRankOrder.add(itr+1);
		}
		System.out.println("currRankOrder size="+currRankOrder.size());
		// Start Playing the Game
		System.out.println("Let the game begin");
		int itr=0;
		int playerId;
		System.out.println("Order in which game would rotate");
		for(Integer order: playingOrder)
			System.out.print(" "+(order+1));
		System.out.println();

		while(true) {
			playerId = playingOrder.get(itr);
			PlayerInfo player = playerInfoMap.get(playerId);
			if(player.oneCount==2) {
				itr = ((itr+1)%playingOrder.size());
				player.oneCount = 0;
				playerInfoMap.put(playerId,player);
				System.out.println("Skipping "+(player.playerName)+" turn");
				continue;
			}
			System.out.println(player.playerName+" it's your turn press R to role");
			readString = scanner.nextLine();
			int currNumber = getRandomNumber(1,6);
			System.out.println(" You got number = "+currNumber);
			player.score+=currNumber;
			playerInfoMap.put(playerId,player);
			printScoreBoard(playerInfoMap,currRankOrder);
			if(player.score>=winingPoints) {
				playingOrder.remove(new Integer(playerId));
				itr = (itr+1)%(playingOrder.size());
				if(itr == playingOrder.size()-1 || itr==0) {
					itr=0;
				} else {
					itr=itr-1;
				}
				player.isGameFinished = true;
				playerInfoMap.put(playerId,player);
				System.out.println("Hurray! You have accumulated Max Points . You came:"+(currRankOrder.get(playerId)+1));
				if(playingOrder.size()==1) {
					break;
				}
				continue;
			}

			if(currNumber == 6) {
				System.out.println("You got a Six Hurray!. Your Chance Again Press R to roll");
				continue;
			} else if(currNumber == 1) {
				player.oneCount+=1;
				playerInfoMap.put(playerId,player);
				if(player.oneCount==2)
					System.out.println("Bad Luck! You would be skipping your next chance");
				else
					System.out.println("Be careful! If you get another one in next chance then you would skip your next to next chance");
			}
			itr = (itr+1)%(playingOrder.size());
		}
		System.out.println("Game Over -- Final Standings");
		finalPrintScoreBoard(playerInfoMap,currRankOrder);
	}

	private static int getRandomNumber(int min, int max) {
		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

	private static void printScoreBoard(HashMap<Integer,PlayerInfo> map,List<Integer> currRankOrder) {
		System.out.println("Score Board");
		int itr=0;
		HashMap<Integer,PlayerInfo> sortedMap = sortByValue(map);
		System.out.println("-----------------------------------------");
		for(Map.Entry<Integer,PlayerInfo> entry:sortedMap.entrySet()) {
			currRankOrder.set(entry.getKey(),itr);
			itr++;
			if(entry.getValue().isGameFinished){
				continue;
			}
			System.out.println((itr+1)+"   "+entry.getValue().playerName+"   "+entry.getValue().score);
		}
		System.out.println("-----------------------------------------");
	}

	private static void finalPrintScoreBoard(HashMap<Integer,PlayerInfo> map,List<Integer> currRankOrder) {
		System.out.println("-----------------------------------------");
		System.out.println("Rank   PlayerName");
		int itr=0;
		for(itr=0;itr<currRankOrder.size();) {
			System.out.println((itr+1)+"   "+map.get(currRankOrder.get(itr)).playerName);
			itr++;
		}
		System.out.println("-----------------------------------------");
	}

	private static HashMap<Integer, PlayerInfo> sortByValue(HashMap<Integer, PlayerInfo> hm) 
    { 
        // Create a list from elements of HashMap 
        List<Map.Entry<Integer, PlayerInfo> > list = 
               new LinkedList<Map.Entry<Integer, PlayerInfo> >(hm.entrySet()); 
  
        // Sort the list 
        Collections.sort(list, new Comparator<Map.Entry<Integer, PlayerInfo> >() { 
            public int compare(Map.Entry<Integer, PlayerInfo> o1,  
                               Map.Entry<Integer, PlayerInfo> o2) 
            { 
                if(o2.getValue().score<o1.getValue().score) {
                	return -1;
                } else if(o2.getValue().score>o1.getValue().score) {
                	return 1;
                }
                return 0;
            } 
        }); 
          
        // put data from sorted list to hashmap  
        HashMap<Integer, PlayerInfo> temp = new LinkedHashMap<Integer, PlayerInfo>(); 
        for (Map.Entry<Integer, PlayerInfo> aa : list) { 
            temp.put(aa.getKey(), aa.getValue()); 
        } 
        return temp; 
    } 

	private static List<Integer> getPlayingOrder(int noPlayers) {
	    List<Integer> playingOrder = new ArrayList<Integer> ();
	    for(int i=0;i<noPlayers;i++) 
	    {
	    	playingOrder.add(i);
	    }
	    Collections.shuffle(playingOrder);
	    return playingOrder;
	}
}
