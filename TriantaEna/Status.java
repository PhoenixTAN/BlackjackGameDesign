
/*
Author: Ziqi Tan
*/
/**
 * Modified by Jiaqian Sun
 * Add FOLD and cancel SPLIT.
 * */
public enum Status {
	// SPLIT,      // Options: hit and stand (split and double up).
	DEFAULT,    // Options: hit and stand (double up).
	BUST,       // > 21 Options: None.
	BLACKJACK,  // 21 Options: None.
	NATURAL,    // Natural blackjack. Options: None.
				
	HASSTAND,  // HASSTAND	
	FOLD
}
