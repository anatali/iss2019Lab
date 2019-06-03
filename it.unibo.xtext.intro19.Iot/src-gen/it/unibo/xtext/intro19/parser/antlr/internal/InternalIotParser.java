package it.unibo.xtext.intro19.parser.antlr.internal;

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import it.unibo.xtext.intro19.services.IotGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalIotParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_STRING", "RULE_INT", "RULE_VARID", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'System'", "'mqttBroker'", "':'", "'Event'", "'Dispatch'"
    };
    public static final int RULE_VARID=7;
    public static final int RULE_ID=4;
    public static final int RULE_WS=10;
    public static final int RULE_STRING=5;
    public static final int RULE_ANY_OTHER=11;
    public static final int RULE_SL_COMMENT=9;
    public static final int T__15=15;
    public static final int T__16=16;
    public static final int RULE_INT=6;
    public static final int RULE_ML_COMMENT=8;
    public static final int T__12=12;
    public static final int T__13=13;
    public static final int T__14=14;
    public static final int EOF=-1;

    // delegates
    // delegators


        public InternalIotParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalIotParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalIotParser.tokenNames; }
    public String getGrammarFileName() { return "InternalIot.g"; }



     	private IotGrammarAccess grammarAccess;

        public InternalIotParser(TokenStream input, IotGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }

        @Override
        protected String getFirstRuleName() {
        	return "IotSystem";
       	}

       	@Override
       	protected IotGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}




    // $ANTLR start "entryRuleIotSystem"
    // InternalIot.g:64:1: entryRuleIotSystem returns [EObject current=null] : iv_ruleIotSystem= ruleIotSystem EOF ;
    public final EObject entryRuleIotSystem() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleIotSystem = null;


        try {
            // InternalIot.g:64:50: (iv_ruleIotSystem= ruleIotSystem EOF )
            // InternalIot.g:65:2: iv_ruleIotSystem= ruleIotSystem EOF
            {
             newCompositeNode(grammarAccess.getIotSystemRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleIotSystem=ruleIotSystem();

            state._fsp--;

             current =iv_ruleIotSystem; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleIotSystem"


    // $ANTLR start "ruleIotSystem"
    // InternalIot.g:71:1: ruleIotSystem returns [EObject current=null] : (otherlv_0= 'System' ( (lv_spec_1_0= ruleIotSystemSpec ) ) ) ;
    public final EObject ruleIotSystem() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        EObject lv_spec_1_0 = null;



        	enterRule();

        try {
            // InternalIot.g:77:2: ( (otherlv_0= 'System' ( (lv_spec_1_0= ruleIotSystemSpec ) ) ) )
            // InternalIot.g:78:2: (otherlv_0= 'System' ( (lv_spec_1_0= ruleIotSystemSpec ) ) )
            {
            // InternalIot.g:78:2: (otherlv_0= 'System' ( (lv_spec_1_0= ruleIotSystemSpec ) ) )
            // InternalIot.g:79:3: otherlv_0= 'System' ( (lv_spec_1_0= ruleIotSystemSpec ) )
            {
            otherlv_0=(Token)match(input,12,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getIotSystemAccess().getSystemKeyword_0());
            		
            // InternalIot.g:83:3: ( (lv_spec_1_0= ruleIotSystemSpec ) )
            // InternalIot.g:84:4: (lv_spec_1_0= ruleIotSystemSpec )
            {
            // InternalIot.g:84:4: (lv_spec_1_0= ruleIotSystemSpec )
            // InternalIot.g:85:5: lv_spec_1_0= ruleIotSystemSpec
            {

            					newCompositeNode(grammarAccess.getIotSystemAccess().getSpecIotSystemSpecParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_2);
            lv_spec_1_0=ruleIotSystemSpec();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getIotSystemRule());
            					}
            					set(
            						current,
            						"spec",
            						lv_spec_1_0,
            						"it.unibo.xtext.intro19.Iot.IotSystemSpec");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleIotSystem"


    // $ANTLR start "entryRuleIotSystemSpec"
    // InternalIot.g:106:1: entryRuleIotSystemSpec returns [EObject current=null] : iv_ruleIotSystemSpec= ruleIotSystemSpec EOF ;
    public final EObject entryRuleIotSystemSpec() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleIotSystemSpec = null;


        try {
            // InternalIot.g:106:54: (iv_ruleIotSystemSpec= ruleIotSystemSpec EOF )
            // InternalIot.g:107:2: iv_ruleIotSystemSpec= ruleIotSystemSpec EOF
            {
             newCompositeNode(grammarAccess.getIotSystemSpecRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleIotSystemSpec=ruleIotSystemSpec();

            state._fsp--;

             current =iv_ruleIotSystemSpec; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleIotSystemSpec"


    // $ANTLR start "ruleIotSystemSpec"
    // InternalIot.g:113:1: ruleIotSystemSpec returns [EObject current=null] : ( ( (lv_name_0_0= RULE_ID ) ) ( (lv_mqttBroker_1_0= ruleBrokerSpec ) )? ( (lv_message_2_0= ruleMessage ) )* ) ;
    public final EObject ruleIotSystemSpec() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        EObject lv_mqttBroker_1_0 = null;

        EObject lv_message_2_0 = null;



        	enterRule();

        try {
            // InternalIot.g:119:2: ( ( ( (lv_name_0_0= RULE_ID ) ) ( (lv_mqttBroker_1_0= ruleBrokerSpec ) )? ( (lv_message_2_0= ruleMessage ) )* ) )
            // InternalIot.g:120:2: ( ( (lv_name_0_0= RULE_ID ) ) ( (lv_mqttBroker_1_0= ruleBrokerSpec ) )? ( (lv_message_2_0= ruleMessage ) )* )
            {
            // InternalIot.g:120:2: ( ( (lv_name_0_0= RULE_ID ) ) ( (lv_mqttBroker_1_0= ruleBrokerSpec ) )? ( (lv_message_2_0= ruleMessage ) )* )
            // InternalIot.g:121:3: ( (lv_name_0_0= RULE_ID ) ) ( (lv_mqttBroker_1_0= ruleBrokerSpec ) )? ( (lv_message_2_0= ruleMessage ) )*
            {
            // InternalIot.g:121:3: ( (lv_name_0_0= RULE_ID ) )
            // InternalIot.g:122:4: (lv_name_0_0= RULE_ID )
            {
            // InternalIot.g:122:4: (lv_name_0_0= RULE_ID )
            // InternalIot.g:123:5: lv_name_0_0= RULE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_4); 

            					newLeafNode(lv_name_0_0, grammarAccess.getIotSystemSpecAccess().getNameIDTerminalRuleCall_0_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getIotSystemSpecRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_0_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            // InternalIot.g:139:3: ( (lv_mqttBroker_1_0= ruleBrokerSpec ) )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==13) ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // InternalIot.g:140:4: (lv_mqttBroker_1_0= ruleBrokerSpec )
                    {
                    // InternalIot.g:140:4: (lv_mqttBroker_1_0= ruleBrokerSpec )
                    // InternalIot.g:141:5: lv_mqttBroker_1_0= ruleBrokerSpec
                    {

                    					newCompositeNode(grammarAccess.getIotSystemSpecAccess().getMqttBrokerBrokerSpecParserRuleCall_1_0());
                    				
                    pushFollow(FOLLOW_5);
                    lv_mqttBroker_1_0=ruleBrokerSpec();

                    state._fsp--;


                    					if (current==null) {
                    						current = createModelElementForParent(grammarAccess.getIotSystemSpecRule());
                    					}
                    					set(
                    						current,
                    						"mqttBroker",
                    						lv_mqttBroker_1_0,
                    						"it.unibo.xtext.intro19.Iot.BrokerSpec");
                    					afterParserOrEnumRuleCall();
                    				

                    }


                    }
                    break;

            }

            // InternalIot.g:158:3: ( (lv_message_2_0= ruleMessage ) )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0>=15 && LA2_0<=16)) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // InternalIot.g:159:4: (lv_message_2_0= ruleMessage )
            	    {
            	    // InternalIot.g:159:4: (lv_message_2_0= ruleMessage )
            	    // InternalIot.g:160:5: lv_message_2_0= ruleMessage
            	    {

            	    					newCompositeNode(grammarAccess.getIotSystemSpecAccess().getMessageMessageParserRuleCall_2_0());
            	    				
            	    pushFollow(FOLLOW_5);
            	    lv_message_2_0=ruleMessage();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getIotSystemSpecRule());
            	    					}
            	    					add(
            	    						current,
            	    						"message",
            	    						lv_message_2_0,
            	    						"it.unibo.xtext.intro19.Iot.Message");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleIotSystemSpec"


    // $ANTLR start "entryRuleBrokerSpec"
    // InternalIot.g:181:1: entryRuleBrokerSpec returns [EObject current=null] : iv_ruleBrokerSpec= ruleBrokerSpec EOF ;
    public final EObject entryRuleBrokerSpec() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleBrokerSpec = null;


        try {
            // InternalIot.g:181:51: (iv_ruleBrokerSpec= ruleBrokerSpec EOF )
            // InternalIot.g:182:2: iv_ruleBrokerSpec= ruleBrokerSpec EOF
            {
             newCompositeNode(grammarAccess.getBrokerSpecRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleBrokerSpec=ruleBrokerSpec();

            state._fsp--;

             current =iv_ruleBrokerSpec; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleBrokerSpec"


    // $ANTLR start "ruleBrokerSpec"
    // InternalIot.g:188:1: ruleBrokerSpec returns [EObject current=null] : (otherlv_0= 'mqttBroker' ( (lv_brokerHost_1_0= RULE_STRING ) ) otherlv_2= ':' ( (lv_brokerPort_3_0= RULE_INT ) ) ) ;
    public final EObject ruleBrokerSpec() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_brokerHost_1_0=null;
        Token otherlv_2=null;
        Token lv_brokerPort_3_0=null;


        	enterRule();

        try {
            // InternalIot.g:194:2: ( (otherlv_0= 'mqttBroker' ( (lv_brokerHost_1_0= RULE_STRING ) ) otherlv_2= ':' ( (lv_brokerPort_3_0= RULE_INT ) ) ) )
            // InternalIot.g:195:2: (otherlv_0= 'mqttBroker' ( (lv_brokerHost_1_0= RULE_STRING ) ) otherlv_2= ':' ( (lv_brokerPort_3_0= RULE_INT ) ) )
            {
            // InternalIot.g:195:2: (otherlv_0= 'mqttBroker' ( (lv_brokerHost_1_0= RULE_STRING ) ) otherlv_2= ':' ( (lv_brokerPort_3_0= RULE_INT ) ) )
            // InternalIot.g:196:3: otherlv_0= 'mqttBroker' ( (lv_brokerHost_1_0= RULE_STRING ) ) otherlv_2= ':' ( (lv_brokerPort_3_0= RULE_INT ) )
            {
            otherlv_0=(Token)match(input,13,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getBrokerSpecAccess().getMqttBrokerKeyword_0());
            		
            // InternalIot.g:200:3: ( (lv_brokerHost_1_0= RULE_STRING ) )
            // InternalIot.g:201:4: (lv_brokerHost_1_0= RULE_STRING )
            {
            // InternalIot.g:201:4: (lv_brokerHost_1_0= RULE_STRING )
            // InternalIot.g:202:5: lv_brokerHost_1_0= RULE_STRING
            {
            lv_brokerHost_1_0=(Token)match(input,RULE_STRING,FOLLOW_7); 

            					newLeafNode(lv_brokerHost_1_0, grammarAccess.getBrokerSpecAccess().getBrokerHostSTRINGTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getBrokerSpecRule());
            					}
            					setWithLastConsumed(
            						current,
            						"brokerHost",
            						lv_brokerHost_1_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            otherlv_2=(Token)match(input,14,FOLLOW_8); 

            			newLeafNode(otherlv_2, grammarAccess.getBrokerSpecAccess().getColonKeyword_2());
            		
            // InternalIot.g:222:3: ( (lv_brokerPort_3_0= RULE_INT ) )
            // InternalIot.g:223:4: (lv_brokerPort_3_0= RULE_INT )
            {
            // InternalIot.g:223:4: (lv_brokerPort_3_0= RULE_INT )
            // InternalIot.g:224:5: lv_brokerPort_3_0= RULE_INT
            {
            lv_brokerPort_3_0=(Token)match(input,RULE_INT,FOLLOW_2); 

            					newLeafNode(lv_brokerPort_3_0, grammarAccess.getBrokerSpecAccess().getBrokerPortINTTerminalRuleCall_3_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getBrokerSpecRule());
            					}
            					setWithLastConsumed(
            						current,
            						"brokerPort",
            						lv_brokerPort_3_0,
            						"org.eclipse.xtext.common.Terminals.INT");
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleBrokerSpec"


    // $ANTLR start "entryRuleMessage"
    // InternalIot.g:244:1: entryRuleMessage returns [EObject current=null] : iv_ruleMessage= ruleMessage EOF ;
    public final EObject entryRuleMessage() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMessage = null;


        try {
            // InternalIot.g:244:48: (iv_ruleMessage= ruleMessage EOF )
            // InternalIot.g:245:2: iv_ruleMessage= ruleMessage EOF
            {
             newCompositeNode(grammarAccess.getMessageRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleMessage=ruleMessage();

            state._fsp--;

             current =iv_ruleMessage; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleMessage"


    // $ANTLR start "ruleMessage"
    // InternalIot.g:251:1: ruleMessage returns [EObject current=null] : (this_Event_0= ruleEvent | this_Dispatch_1= ruleDispatch ) ;
    public final EObject ruleMessage() throws RecognitionException {
        EObject current = null;

        EObject this_Event_0 = null;

        EObject this_Dispatch_1 = null;



        	enterRule();

        try {
            // InternalIot.g:257:2: ( (this_Event_0= ruleEvent | this_Dispatch_1= ruleDispatch ) )
            // InternalIot.g:258:2: (this_Event_0= ruleEvent | this_Dispatch_1= ruleDispatch )
            {
            // InternalIot.g:258:2: (this_Event_0= ruleEvent | this_Dispatch_1= ruleDispatch )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==15) ) {
                alt3=1;
            }
            else if ( (LA3_0==16) ) {
                alt3=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // InternalIot.g:259:3: this_Event_0= ruleEvent
                    {

                    			newCompositeNode(grammarAccess.getMessageAccess().getEventParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_Event_0=ruleEvent();

                    state._fsp--;


                    			current = this_Event_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalIot.g:268:3: this_Dispatch_1= ruleDispatch
                    {

                    			newCompositeNode(grammarAccess.getMessageAccess().getDispatchParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_Dispatch_1=ruleDispatch();

                    state._fsp--;


                    			current = this_Dispatch_1;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleMessage"


    // $ANTLR start "entryRuleEvent"
    // InternalIot.g:280:1: entryRuleEvent returns [EObject current=null] : iv_ruleEvent= ruleEvent EOF ;
    public final EObject entryRuleEvent() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEvent = null;


        try {
            // InternalIot.g:280:46: (iv_ruleEvent= ruleEvent EOF )
            // InternalIot.g:281:2: iv_ruleEvent= ruleEvent EOF
            {
             newCompositeNode(grammarAccess.getEventRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleEvent=ruleEvent();

            state._fsp--;

             current =iv_ruleEvent; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleEvent"


    // $ANTLR start "ruleEvent"
    // InternalIot.g:287:1: ruleEvent returns [EObject current=null] : (otherlv_0= 'Event' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (lv_msg_3_0= RULE_STRING ) ) ) ;
    public final EObject ruleEvent() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token lv_msg_3_0=null;


        	enterRule();

        try {
            // InternalIot.g:293:2: ( (otherlv_0= 'Event' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (lv_msg_3_0= RULE_STRING ) ) ) )
            // InternalIot.g:294:2: (otherlv_0= 'Event' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (lv_msg_3_0= RULE_STRING ) ) )
            {
            // InternalIot.g:294:2: (otherlv_0= 'Event' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (lv_msg_3_0= RULE_STRING ) ) )
            // InternalIot.g:295:3: otherlv_0= 'Event' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (lv_msg_3_0= RULE_STRING ) )
            {
            otherlv_0=(Token)match(input,15,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getEventAccess().getEventKeyword_0());
            		
            // InternalIot.g:299:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalIot.g:300:4: (lv_name_1_0= RULE_ID )
            {
            // InternalIot.g:300:4: (lv_name_1_0= RULE_ID )
            // InternalIot.g:301:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_7); 

            					newLeafNode(lv_name_1_0, grammarAccess.getEventAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getEventRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            otherlv_2=(Token)match(input,14,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getEventAccess().getColonKeyword_2());
            		
            // InternalIot.g:321:3: ( (lv_msg_3_0= RULE_STRING ) )
            // InternalIot.g:322:4: (lv_msg_3_0= RULE_STRING )
            {
            // InternalIot.g:322:4: (lv_msg_3_0= RULE_STRING )
            // InternalIot.g:323:5: lv_msg_3_0= RULE_STRING
            {
            lv_msg_3_0=(Token)match(input,RULE_STRING,FOLLOW_2); 

            					newLeafNode(lv_msg_3_0, grammarAccess.getEventAccess().getMsgSTRINGTerminalRuleCall_3_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getEventRule());
            					}
            					setWithLastConsumed(
            						current,
            						"msg",
            						lv_msg_3_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleEvent"


    // $ANTLR start "entryRuleDispatch"
    // InternalIot.g:343:1: entryRuleDispatch returns [EObject current=null] : iv_ruleDispatch= ruleDispatch EOF ;
    public final EObject entryRuleDispatch() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDispatch = null;


        try {
            // InternalIot.g:343:49: (iv_ruleDispatch= ruleDispatch EOF )
            // InternalIot.g:344:2: iv_ruleDispatch= ruleDispatch EOF
            {
             newCompositeNode(grammarAccess.getDispatchRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleDispatch=ruleDispatch();

            state._fsp--;

             current =iv_ruleDispatch; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleDispatch"


    // $ANTLR start "ruleDispatch"
    // InternalIot.g:350:1: ruleDispatch returns [EObject current=null] : (otherlv_0= 'Dispatch' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (lv_msg_3_0= RULE_STRING ) ) ) ;
    public final EObject ruleDispatch() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token lv_msg_3_0=null;


        	enterRule();

        try {
            // InternalIot.g:356:2: ( (otherlv_0= 'Dispatch' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (lv_msg_3_0= RULE_STRING ) ) ) )
            // InternalIot.g:357:2: (otherlv_0= 'Dispatch' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (lv_msg_3_0= RULE_STRING ) ) )
            {
            // InternalIot.g:357:2: (otherlv_0= 'Dispatch' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (lv_msg_3_0= RULE_STRING ) ) )
            // InternalIot.g:358:3: otherlv_0= 'Dispatch' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (lv_msg_3_0= RULE_STRING ) )
            {
            otherlv_0=(Token)match(input,16,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getDispatchAccess().getDispatchKeyword_0());
            		
            // InternalIot.g:362:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalIot.g:363:4: (lv_name_1_0= RULE_ID )
            {
            // InternalIot.g:363:4: (lv_name_1_0= RULE_ID )
            // InternalIot.g:364:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_7); 

            					newLeafNode(lv_name_1_0, grammarAccess.getDispatchAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getDispatchRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            otherlv_2=(Token)match(input,14,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getDispatchAccess().getColonKeyword_2());
            		
            // InternalIot.g:384:3: ( (lv_msg_3_0= RULE_STRING ) )
            // InternalIot.g:385:4: (lv_msg_3_0= RULE_STRING )
            {
            // InternalIot.g:385:4: (lv_msg_3_0= RULE_STRING )
            // InternalIot.g:386:5: lv_msg_3_0= RULE_STRING
            {
            lv_msg_3_0=(Token)match(input,RULE_STRING,FOLLOW_2); 

            					newLeafNode(lv_msg_3_0, grammarAccess.getDispatchAccess().getMsgSTRINGTerminalRuleCall_3_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getDispatchRule());
            					}
            					setWithLastConsumed(
            						current,
            						"msg",
            						lv_msg_3_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDispatch"

    // Delegated rules


 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x000000000001A002L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000000018002L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000000000040L});

}