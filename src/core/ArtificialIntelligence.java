package core;

public class ArtificialIntelligence implements Runnable {
	private PositionBoard positionBoard;
	private GameSetting gameSetting;

	public ArtificialIntelligence(GameSetting gameSetting, PositionBoard positionBoard) {
		this.positionBoard = positionBoard;
		this.gameSetting = gameSetting;
	}

	public PositionBoard getNextPosition() {
		this.run();
		
		/////////////////////////////////////////
		if(positionBoard.getChoisePiecesHuman(positionBoard.getBestChild().getParentMove().getNumberNext()) != null) {
			positionBoard.getBoardPanel().setPieceHumanDie(positionBoard.getChoisePiecesHuman(positionBoard.getBestChild().getParentMove().getNumberNext()).getName() 
					+ positionBoard.getChoisePiecesHuman(positionBoard.getBestChild().getParentMove().getNumberNext()).getColor());
		}
		
		return positionBoard.getBestChild();
	}

	public void free() {
		try {
			this.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	private int AlphaBeta(int alpha, int beta, PositionBoard positionBoard1) {
		positionBoard1.setValue(); // value = AiValue - HumanValue
		if (positionBoard1.getDepth() != 0) {
			if (positionBoard1.getValue() < 15000 && positionBoard1.getValue() > -15000) {
				positionBoard1.setListChildPosition();
			}
		}
		
		int alphaBeta = 0;
		if (positionBoard1.getDepth() == 0 || positionBoard1.getListChildPosition().size() == 0) {
			alphaBeta = positionBoard1.getValue();
			return alphaBeta;
		}

		if ((this.gameSetting.getLevel() - positionBoard1.getDepth()) % 2 == 0) {
			int bestChild = -10000000;

			for(int i = 0; i < positionBoard1.getListChildPosition().size(); i++) {
				if (bestChild > alpha) {
					alpha = bestChild;
				}
				
				int value = AlphaBeta(alpha, beta, positionBoard1.getListChildPosition().get(i));
				if (value > bestChild) {
					bestChild = value;
					positionBoard1.setBestChild(positionBoard1.getListChildPosition().get(i));
				}
				
				if (beta <= alpha) break;
			}
			alphaBeta = bestChild;
		}
		if ((this.gameSetting.getLevel() - positionBoard1.getDepth()) % 2 == 1) {
			int bestChild = 10000000;

			for(int i = 0; i < positionBoard1.getListChildPosition().size(); i++) {
				if (bestChild < beta) {
					beta = bestChild;
				}
				
				int value = AlphaBeta(alpha, beta, positionBoard1.getListChildPosition().get(i));
				if (value < bestChild) {
					bestChild = value;
					positionBoard1.setBestChild(positionBoard1.getListChildPosition().get(i));
				}
				
				if (beta <= alpha) break;
			}
			alphaBeta = bestChild;
		}
		positionBoard1.free();
		return alphaBeta;

	}

	@Override
	public void run() {
		if (GameSetting.rootLevel == 6) {
			gameSetting.setLevel(4);
			PositionBoard positionBoard2 = positionBoard.copy(positionBoard.getDepth(), gameSetting);
			positionBoard2.setListChildPosition();
			if (positionBoard2.getListChildPosition().size() <= 20) {
				gameSetting.setLevel(5);
			}
			boolean isHauLife = false;
			for (int i = 0; i < this.positionBoard.getListPiecesAi().size(); i++) {
				if (this.positionBoard.getListPiecesAi().get(i).getName().equals("Hau")) {
					isHauLife = true;
				}
			}
			for (int i = 0; i < this.positionBoard.getListPiecesHuman().size(); i++) {
				if (this.positionBoard.getListPiecesHuman().get(i).getName().equals("Hau")) {
					isHauLife = true;
				}
			}
			if (isHauLife == false) {
				PositionBoard positionBoard3 = positionBoard.copy(positionBoard.getDepth(), gameSetting);
				positionBoard3.setListChildPosition();
				if (positionBoard3.getListChildPosition().size() <= 12) {
					gameSetting.setLevel(6);
				}
				
			}
			this.positionBoard.setDepth(gameSetting.getLevel());
		}
		AlphaBeta(-10000000, 10000000, this.positionBoard);
	}
}
