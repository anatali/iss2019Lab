package it.unibo.xtext.intro19.ide.contentassist.antlr.internal;

import java.io.InputStream;
import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.DFA;
import it.unibo.xtext.intro19.services.IotGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalIotParser extends AbstractInternalContentAssistParser {
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

    	public void setGrammarAccess(IotGrammarAccess grammarAccess) {
    		this.grammarAccess = grammarAccess;
    	}

    	@Override
    	protected Grammar getGrammar() {
    		return grammarAccess.getGrammar();
    	}

    	@Override
    	protected String getValueForTokenName(String tokenName) {
    		return tokenName;
    	}



    // $ANTLR start "entryRuleIotSystem"
    // InternalIot.g:53:1: entryRuleIotSystem : ruleIotSystem EOF ;
    public final void entryRuleIotSystem() throws RecognitionException {
        try {
            // InternalIot.g:54:1: ( ruleIotSystem EOF )
            // InternalIot.g:55:1: ruleIotSystem EOF
            {
             before(grammarAccess.getIotSystemRule()); 
            pushFollow(FOLLOW_1);
            ruleIotSystem();

            state._fsp--;

             after(grammarAccess.getIotSystemRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleIotSystem"


    // $ANTLR start "ruleIotSystem"
    // InternalIot.g:62:1: ruleIotSystem : ( ( rule__IotSystem__Group__0 ) ) ;
    public final void ruleIotSystem() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalIot.g:66:2: ( ( ( rule__IotSystem__Group__0 ) ) )
            // InternalIot.g:67:2: ( ( rule__IotSystem__Group__0 ) )
            {
            // InternalIot.g:67:2: ( ( rule__IotSystem__Group__0 ) )
            // InternalIot.g:68:3: ( rule__IotSystem__Group__0 )
            {
             before(grammarAccess.getIotSystemAccess().getGroup()); 
            // InternalIot.g:69:3: ( rule__IotSystem__Group__0 )
            // InternalIot.g:69:4: rule__IotSystem__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__IotSystem__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getIotSystemAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleIotSystem"


    // $ANTLR start "entryRuleIotSystemSpec"
    // InternalIot.g:78:1: entryRuleIotSystemSpec : ruleIotSystemSpec EOF ;
    public final void entryRuleIotSystemSpec() throws RecognitionException {
        try {
            // InternalIot.g:79:1: ( ruleIotSystemSpec EOF )
            // InternalIot.g:80:1: ruleIotSystemSpec EOF
            {
             before(grammarAccess.getIotSystemSpecRule()); 
            pushFollow(FOLLOW_1);
            ruleIotSystemSpec();

            state._fsp--;

             after(grammarAccess.getIotSystemSpecRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleIotSystemSpec"


    // $ANTLR start "ruleIotSystemSpec"
    // InternalIot.g:87:1: ruleIotSystemSpec : ( ( rule__IotSystemSpec__Group__0 ) ) ;
    public final void ruleIotSystemSpec() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalIot.g:91:2: ( ( ( rule__IotSystemSpec__Group__0 ) ) )
            // InternalIot.g:92:2: ( ( rule__IotSystemSpec__Group__0 ) )
            {
            // InternalIot.g:92:2: ( ( rule__IotSystemSpec__Group__0 ) )
            // InternalIot.g:93:3: ( rule__IotSystemSpec__Group__0 )
            {
             before(grammarAccess.getIotSystemSpecAccess().getGroup()); 
            // InternalIot.g:94:3: ( rule__IotSystemSpec__Group__0 )
            // InternalIot.g:94:4: rule__IotSystemSpec__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__IotSystemSpec__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getIotSystemSpecAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleIotSystemSpec"


    // $ANTLR start "entryRuleBrokerSpec"
    // InternalIot.g:103:1: entryRuleBrokerSpec : ruleBrokerSpec EOF ;
    public final void entryRuleBrokerSpec() throws RecognitionException {
        try {
            // InternalIot.g:104:1: ( ruleBrokerSpec EOF )
            // InternalIot.g:105:1: ruleBrokerSpec EOF
            {
             before(grammarAccess.getBrokerSpecRule()); 
            pushFollow(FOLLOW_1);
            ruleBrokerSpec();

            state._fsp--;

             after(grammarAccess.getBrokerSpecRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleBrokerSpec"


    // $ANTLR start "ruleBrokerSpec"
    // InternalIot.g:112:1: ruleBrokerSpec : ( ( rule__BrokerSpec__Group__0 ) ) ;
    public final void ruleBrokerSpec() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalIot.g:116:2: ( ( ( rule__BrokerSpec__Group__0 ) ) )
            // InternalIot.g:117:2: ( ( rule__BrokerSpec__Group__0 ) )
            {
            // InternalIot.g:117:2: ( ( rule__BrokerSpec__Group__0 ) )
            // InternalIot.g:118:3: ( rule__BrokerSpec__Group__0 )
            {
             before(grammarAccess.getBrokerSpecAccess().getGroup()); 
            // InternalIot.g:119:3: ( rule__BrokerSpec__Group__0 )
            // InternalIot.g:119:4: rule__BrokerSpec__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__BrokerSpec__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getBrokerSpecAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleBrokerSpec"


    // $ANTLR start "entryRuleMessage"
    // InternalIot.g:128:1: entryRuleMessage : ruleMessage EOF ;
    public final void entryRuleMessage() throws RecognitionException {
        try {
            // InternalIot.g:129:1: ( ruleMessage EOF )
            // InternalIot.g:130:1: ruleMessage EOF
            {
             before(grammarAccess.getMessageRule()); 
            pushFollow(FOLLOW_1);
            ruleMessage();

            state._fsp--;

             after(grammarAccess.getMessageRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleMessage"


    // $ANTLR start "ruleMessage"
    // InternalIot.g:137:1: ruleMessage : ( ( rule__Message__Alternatives ) ) ;
    public final void ruleMessage() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalIot.g:141:2: ( ( ( rule__Message__Alternatives ) ) )
            // InternalIot.g:142:2: ( ( rule__Message__Alternatives ) )
            {
            // InternalIot.g:142:2: ( ( rule__Message__Alternatives ) )
            // InternalIot.g:143:3: ( rule__Message__Alternatives )
            {
             before(grammarAccess.getMessageAccess().getAlternatives()); 
            // InternalIot.g:144:3: ( rule__Message__Alternatives )
            // InternalIot.g:144:4: rule__Message__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Message__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getMessageAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleMessage"


    // $ANTLR start "entryRuleEvent"
    // InternalIot.g:153:1: entryRuleEvent : ruleEvent EOF ;
    public final void entryRuleEvent() throws RecognitionException {
        try {
            // InternalIot.g:154:1: ( ruleEvent EOF )
            // InternalIot.g:155:1: ruleEvent EOF
            {
             before(grammarAccess.getEventRule()); 
            pushFollow(FOLLOW_1);
            ruleEvent();

            state._fsp--;

             after(grammarAccess.getEventRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleEvent"


    // $ANTLR start "ruleEvent"
    // InternalIot.g:162:1: ruleEvent : ( ( rule__Event__Group__0 ) ) ;
    public final void ruleEvent() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalIot.g:166:2: ( ( ( rule__Event__Group__0 ) ) )
            // InternalIot.g:167:2: ( ( rule__Event__Group__0 ) )
            {
            // InternalIot.g:167:2: ( ( rule__Event__Group__0 ) )
            // InternalIot.g:168:3: ( rule__Event__Group__0 )
            {
             before(grammarAccess.getEventAccess().getGroup()); 
            // InternalIot.g:169:3: ( rule__Event__Group__0 )
            // InternalIot.g:169:4: rule__Event__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Event__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getEventAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleEvent"


    // $ANTLR start "entryRuleDispatch"
    // InternalIot.g:178:1: entryRuleDispatch : ruleDispatch EOF ;
    public final void entryRuleDispatch() throws RecognitionException {
        try {
            // InternalIot.g:179:1: ( ruleDispatch EOF )
            // InternalIot.g:180:1: ruleDispatch EOF
            {
             before(grammarAccess.getDispatchRule()); 
            pushFollow(FOLLOW_1);
            ruleDispatch();

            state._fsp--;

             after(grammarAccess.getDispatchRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleDispatch"


    // $ANTLR start "ruleDispatch"
    // InternalIot.g:187:1: ruleDispatch : ( ( rule__Dispatch__Group__0 ) ) ;
    public final void ruleDispatch() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalIot.g:191:2: ( ( ( rule__Dispatch__Group__0 ) ) )
            // InternalIot.g:192:2: ( ( rule__Dispatch__Group__0 ) )
            {
            // InternalIot.g:192:2: ( ( rule__Dispatch__Group__0 ) )
            // InternalIot.g:193:3: ( rule__Dispatch__Group__0 )
            {
             before(grammarAccess.getDispatchAccess().getGroup()); 
            // InternalIot.g:194:3: ( rule__Dispatch__Group__0 )
            // InternalIot.g:194:4: rule__Dispatch__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Dispatch__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getDispatchAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleDispatch"


    // $ANTLR start "rule__Message__Alternatives"
    // InternalIot.g:202:1: rule__Message__Alternatives : ( ( ruleEvent ) | ( ruleDispatch ) );
    public final void rule__Message__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalIot.g:206:1: ( ( ruleEvent ) | ( ruleDispatch ) )
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==15) ) {
                alt1=1;
            }
            else if ( (LA1_0==16) ) {
                alt1=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }
            switch (alt1) {
                case 1 :
                    // InternalIot.g:207:2: ( ruleEvent )
                    {
                    // InternalIot.g:207:2: ( ruleEvent )
                    // InternalIot.g:208:3: ruleEvent
                    {
                     before(grammarAccess.getMessageAccess().getEventParserRuleCall_0()); 
                    pushFollow(FOLLOW_2);
                    ruleEvent();

                    state._fsp--;

                     after(grammarAccess.getMessageAccess().getEventParserRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalIot.g:213:2: ( ruleDispatch )
                    {
                    // InternalIot.g:213:2: ( ruleDispatch )
                    // InternalIot.g:214:3: ruleDispatch
                    {
                     before(grammarAccess.getMessageAccess().getDispatchParserRuleCall_1()); 
                    pushFollow(FOLLOW_2);
                    ruleDispatch();

                    state._fsp--;

                     after(grammarAccess.getMessageAccess().getDispatchParserRuleCall_1()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Message__Alternatives"


    // $ANTLR start "rule__IotSystem__Group__0"
    // InternalIot.g:223:1: rule__IotSystem__Group__0 : rule__IotSystem__Group__0__Impl rule__IotSystem__Group__1 ;
    public final void rule__IotSystem__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalIot.g:227:1: ( rule__IotSystem__Group__0__Impl rule__IotSystem__Group__1 )
            // InternalIot.g:228:2: rule__IotSystem__Group__0__Impl rule__IotSystem__Group__1
            {
            pushFollow(FOLLOW_3);
            rule__IotSystem__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__IotSystem__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IotSystem__Group__0"


    // $ANTLR start "rule__IotSystem__Group__0__Impl"
    // InternalIot.g:235:1: rule__IotSystem__Group__0__Impl : ( 'System' ) ;
    public final void rule__IotSystem__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalIot.g:239:1: ( ( 'System' ) )
            // InternalIot.g:240:1: ( 'System' )
            {
            // InternalIot.g:240:1: ( 'System' )
            // InternalIot.g:241:2: 'System'
            {
             before(grammarAccess.getIotSystemAccess().getSystemKeyword_0()); 
            match(input,12,FOLLOW_2); 
             after(grammarAccess.getIotSystemAccess().getSystemKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IotSystem__Group__0__Impl"


    // $ANTLR start "rule__IotSystem__Group__1"
    // InternalIot.g:250:1: rule__IotSystem__Group__1 : rule__IotSystem__Group__1__Impl ;
    public final void rule__IotSystem__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalIot.g:254:1: ( rule__IotSystem__Group__1__Impl )
            // InternalIot.g:255:2: rule__IotSystem__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__IotSystem__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IotSystem__Group__1"


    // $ANTLR start "rule__IotSystem__Group__1__Impl"
    // InternalIot.g:261:1: rule__IotSystem__Group__1__Impl : ( ( rule__IotSystem__SpecAssignment_1 ) ) ;
    public final void rule__IotSystem__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalIot.g:265:1: ( ( ( rule__IotSystem__SpecAssignment_1 ) ) )
            // InternalIot.g:266:1: ( ( rule__IotSystem__SpecAssignment_1 ) )
            {
            // InternalIot.g:266:1: ( ( rule__IotSystem__SpecAssignment_1 ) )
            // InternalIot.g:267:2: ( rule__IotSystem__SpecAssignment_1 )
            {
             before(grammarAccess.getIotSystemAccess().getSpecAssignment_1()); 
            // InternalIot.g:268:2: ( rule__IotSystem__SpecAssignment_1 )
            // InternalIot.g:268:3: rule__IotSystem__SpecAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__IotSystem__SpecAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getIotSystemAccess().getSpecAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IotSystem__Group__1__Impl"


    // $ANTLR start "rule__IotSystemSpec__Group__0"
    // InternalIot.g:277:1: rule__IotSystemSpec__Group__0 : rule__IotSystemSpec__Group__0__Impl rule__IotSystemSpec__Group__1 ;
    public final void rule__IotSystemSpec__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalIot.g:281:1: ( rule__IotSystemSpec__Group__0__Impl rule__IotSystemSpec__Group__1 )
            // InternalIot.g:282:2: rule__IotSystemSpec__Group__0__Impl rule__IotSystemSpec__Group__1
            {
            pushFollow(FOLLOW_4);
            rule__IotSystemSpec__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__IotSystemSpec__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IotSystemSpec__Group__0"


    // $ANTLR start "rule__IotSystemSpec__Group__0__Impl"
    // InternalIot.g:289:1: rule__IotSystemSpec__Group__0__Impl : ( ( rule__IotSystemSpec__NameAssignment_0 ) ) ;
    public final void rule__IotSystemSpec__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalIot.g:293:1: ( ( ( rule__IotSystemSpec__NameAssignment_0 ) ) )
            // InternalIot.g:294:1: ( ( rule__IotSystemSpec__NameAssignment_0 ) )
            {
            // InternalIot.g:294:1: ( ( rule__IotSystemSpec__NameAssignment_0 ) )
            // InternalIot.g:295:2: ( rule__IotSystemSpec__NameAssignment_0 )
            {
             before(grammarAccess.getIotSystemSpecAccess().getNameAssignment_0()); 
            // InternalIot.g:296:2: ( rule__IotSystemSpec__NameAssignment_0 )
            // InternalIot.g:296:3: rule__IotSystemSpec__NameAssignment_0
            {
            pushFollow(FOLLOW_2);
            rule__IotSystemSpec__NameAssignment_0();

            state._fsp--;


            }

             after(grammarAccess.getIotSystemSpecAccess().getNameAssignment_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IotSystemSpec__Group__0__Impl"


    // $ANTLR start "rule__IotSystemSpec__Group__1"
    // InternalIot.g:304:1: rule__IotSystemSpec__Group__1 : rule__IotSystemSpec__Group__1__Impl rule__IotSystemSpec__Group__2 ;
    public final void rule__IotSystemSpec__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalIot.g:308:1: ( rule__IotSystemSpec__Group__1__Impl rule__IotSystemSpec__Group__2 )
            // InternalIot.g:309:2: rule__IotSystemSpec__Group__1__Impl rule__IotSystemSpec__Group__2
            {
            pushFollow(FOLLOW_4);
            rule__IotSystemSpec__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__IotSystemSpec__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IotSystemSpec__Group__1"


    // $ANTLR start "rule__IotSystemSpec__Group__1__Impl"
    // InternalIot.g:316:1: rule__IotSystemSpec__Group__1__Impl : ( ( rule__IotSystemSpec__MqttBrokerAssignment_1 )? ) ;
    public final void rule__IotSystemSpec__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalIot.g:320:1: ( ( ( rule__IotSystemSpec__MqttBrokerAssignment_1 )? ) )
            // InternalIot.g:321:1: ( ( rule__IotSystemSpec__MqttBrokerAssignment_1 )? )
            {
            // InternalIot.g:321:1: ( ( rule__IotSystemSpec__MqttBrokerAssignment_1 )? )
            // InternalIot.g:322:2: ( rule__IotSystemSpec__MqttBrokerAssignment_1 )?
            {
             before(grammarAccess.getIotSystemSpecAccess().getMqttBrokerAssignment_1()); 
            // InternalIot.g:323:2: ( rule__IotSystemSpec__MqttBrokerAssignment_1 )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==13) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // InternalIot.g:323:3: rule__IotSystemSpec__MqttBrokerAssignment_1
                    {
                    pushFollow(FOLLOW_2);
                    rule__IotSystemSpec__MqttBrokerAssignment_1();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getIotSystemSpecAccess().getMqttBrokerAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IotSystemSpec__Group__1__Impl"


    // $ANTLR start "rule__IotSystemSpec__Group__2"
    // InternalIot.g:331:1: rule__IotSystemSpec__Group__2 : rule__IotSystemSpec__Group__2__Impl ;
    public final void rule__IotSystemSpec__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalIot.g:335:1: ( rule__IotSystemSpec__Group__2__Impl )
            // InternalIot.g:336:2: rule__IotSystemSpec__Group__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__IotSystemSpec__Group__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IotSystemSpec__Group__2"


    // $ANTLR start "rule__IotSystemSpec__Group__2__Impl"
    // InternalIot.g:342:1: rule__IotSystemSpec__Group__2__Impl : ( ( rule__IotSystemSpec__MessageAssignment_2 )* ) ;
    public final void rule__IotSystemSpec__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalIot.g:346:1: ( ( ( rule__IotSystemSpec__MessageAssignment_2 )* ) )
            // InternalIot.g:347:1: ( ( rule__IotSystemSpec__MessageAssignment_2 )* )
            {
            // InternalIot.g:347:1: ( ( rule__IotSystemSpec__MessageAssignment_2 )* )
            // InternalIot.g:348:2: ( rule__IotSystemSpec__MessageAssignment_2 )*
            {
             before(grammarAccess.getIotSystemSpecAccess().getMessageAssignment_2()); 
            // InternalIot.g:349:2: ( rule__IotSystemSpec__MessageAssignment_2 )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0>=15 && LA3_0<=16)) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // InternalIot.g:349:3: rule__IotSystemSpec__MessageAssignment_2
            	    {
            	    pushFollow(FOLLOW_5);
            	    rule__IotSystemSpec__MessageAssignment_2();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);

             after(grammarAccess.getIotSystemSpecAccess().getMessageAssignment_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IotSystemSpec__Group__2__Impl"


    // $ANTLR start "rule__BrokerSpec__Group__0"
    // InternalIot.g:358:1: rule__BrokerSpec__Group__0 : rule__BrokerSpec__Group__0__Impl rule__BrokerSpec__Group__1 ;
    public final void rule__BrokerSpec__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalIot.g:362:1: ( rule__BrokerSpec__Group__0__Impl rule__BrokerSpec__Group__1 )
            // InternalIot.g:363:2: rule__BrokerSpec__Group__0__Impl rule__BrokerSpec__Group__1
            {
            pushFollow(FOLLOW_6);
            rule__BrokerSpec__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__BrokerSpec__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__BrokerSpec__Group__0"


    // $ANTLR start "rule__BrokerSpec__Group__0__Impl"
    // InternalIot.g:370:1: rule__BrokerSpec__Group__0__Impl : ( 'mqttBroker' ) ;
    public final void rule__BrokerSpec__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalIot.g:374:1: ( ( 'mqttBroker' ) )
            // InternalIot.g:375:1: ( 'mqttBroker' )
            {
            // InternalIot.g:375:1: ( 'mqttBroker' )
            // InternalIot.g:376:2: 'mqttBroker'
            {
             before(grammarAccess.getBrokerSpecAccess().getMqttBrokerKeyword_0()); 
            match(input,13,FOLLOW_2); 
             after(grammarAccess.getBrokerSpecAccess().getMqttBrokerKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__BrokerSpec__Group__0__Impl"


    // $ANTLR start "rule__BrokerSpec__Group__1"
    // InternalIot.g:385:1: rule__BrokerSpec__Group__1 : rule__BrokerSpec__Group__1__Impl rule__BrokerSpec__Group__2 ;
    public final void rule__BrokerSpec__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalIot.g:389:1: ( rule__BrokerSpec__Group__1__Impl rule__BrokerSpec__Group__2 )
            // InternalIot.g:390:2: rule__BrokerSpec__Group__1__Impl rule__BrokerSpec__Group__2
            {
            pushFollow(FOLLOW_7);
            rule__BrokerSpec__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__BrokerSpec__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__BrokerSpec__Group__1"


    // $ANTLR start "rule__BrokerSpec__Group__1__Impl"
    // InternalIot.g:397:1: rule__BrokerSpec__Group__1__Impl : ( ( rule__BrokerSpec__BrokerHostAssignment_1 ) ) ;
    public final void rule__BrokerSpec__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalIot.g:401:1: ( ( ( rule__BrokerSpec__BrokerHostAssignment_1 ) ) )
            // InternalIot.g:402:1: ( ( rule__BrokerSpec__BrokerHostAssignment_1 ) )
            {
            // InternalIot.g:402:1: ( ( rule__BrokerSpec__BrokerHostAssignment_1 ) )
            // InternalIot.g:403:2: ( rule__BrokerSpec__BrokerHostAssignment_1 )
            {
             before(grammarAccess.getBrokerSpecAccess().getBrokerHostAssignment_1()); 
            // InternalIot.g:404:2: ( rule__BrokerSpec__BrokerHostAssignment_1 )
            // InternalIot.g:404:3: rule__BrokerSpec__BrokerHostAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__BrokerSpec__BrokerHostAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getBrokerSpecAccess().getBrokerHostAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__BrokerSpec__Group__1__Impl"


    // $ANTLR start "rule__BrokerSpec__Group__2"
    // InternalIot.g:412:1: rule__BrokerSpec__Group__2 : rule__BrokerSpec__Group__2__Impl rule__BrokerSpec__Group__3 ;
    public final void rule__BrokerSpec__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalIot.g:416:1: ( rule__BrokerSpec__Group__2__Impl rule__BrokerSpec__Group__3 )
            // InternalIot.g:417:2: rule__BrokerSpec__Group__2__Impl rule__BrokerSpec__Group__3
            {
            pushFollow(FOLLOW_8);
            rule__BrokerSpec__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__BrokerSpec__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__BrokerSpec__Group__2"


    // $ANTLR start "rule__BrokerSpec__Group__2__Impl"
    // InternalIot.g:424:1: rule__BrokerSpec__Group__2__Impl : ( ':' ) ;
    public final void rule__BrokerSpec__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalIot.g:428:1: ( ( ':' ) )
            // InternalIot.g:429:1: ( ':' )
            {
            // InternalIot.g:429:1: ( ':' )
            // InternalIot.g:430:2: ':'
            {
             before(grammarAccess.getBrokerSpecAccess().getColonKeyword_2()); 
            match(input,14,FOLLOW_2); 
             after(grammarAccess.getBrokerSpecAccess().getColonKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__BrokerSpec__Group__2__Impl"


    // $ANTLR start "rule__BrokerSpec__Group__3"
    // InternalIot.g:439:1: rule__BrokerSpec__Group__3 : rule__BrokerSpec__Group__3__Impl ;
    public final void rule__BrokerSpec__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalIot.g:443:1: ( rule__BrokerSpec__Group__3__Impl )
            // InternalIot.g:444:2: rule__BrokerSpec__Group__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__BrokerSpec__Group__3__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__BrokerSpec__Group__3"


    // $ANTLR start "rule__BrokerSpec__Group__3__Impl"
    // InternalIot.g:450:1: rule__BrokerSpec__Group__3__Impl : ( ( rule__BrokerSpec__BrokerPortAssignment_3 ) ) ;
    public final void rule__BrokerSpec__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalIot.g:454:1: ( ( ( rule__BrokerSpec__BrokerPortAssignment_3 ) ) )
            // InternalIot.g:455:1: ( ( rule__BrokerSpec__BrokerPortAssignment_3 ) )
            {
            // InternalIot.g:455:1: ( ( rule__BrokerSpec__BrokerPortAssignment_3 ) )
            // InternalIot.g:456:2: ( rule__BrokerSpec__BrokerPortAssignment_3 )
            {
             before(grammarAccess.getBrokerSpecAccess().getBrokerPortAssignment_3()); 
            // InternalIot.g:457:2: ( rule__BrokerSpec__BrokerPortAssignment_3 )
            // InternalIot.g:457:3: rule__BrokerSpec__BrokerPortAssignment_3
            {
            pushFollow(FOLLOW_2);
            rule__BrokerSpec__BrokerPortAssignment_3();

            state._fsp--;


            }

             after(grammarAccess.getBrokerSpecAccess().getBrokerPortAssignment_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__BrokerSpec__Group__3__Impl"


    // $ANTLR start "rule__Event__Group__0"
    // InternalIot.g:466:1: rule__Event__Group__0 : rule__Event__Group__0__Impl rule__Event__Group__1 ;
    public final void rule__Event__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalIot.g:470:1: ( rule__Event__Group__0__Impl rule__Event__Group__1 )
            // InternalIot.g:471:2: rule__Event__Group__0__Impl rule__Event__Group__1
            {
            pushFollow(FOLLOW_3);
            rule__Event__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Event__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Event__Group__0"


    // $ANTLR start "rule__Event__Group__0__Impl"
    // InternalIot.g:478:1: rule__Event__Group__0__Impl : ( 'Event' ) ;
    public final void rule__Event__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalIot.g:482:1: ( ( 'Event' ) )
            // InternalIot.g:483:1: ( 'Event' )
            {
            // InternalIot.g:483:1: ( 'Event' )
            // InternalIot.g:484:2: 'Event'
            {
             before(grammarAccess.getEventAccess().getEventKeyword_0()); 
            match(input,15,FOLLOW_2); 
             after(grammarAccess.getEventAccess().getEventKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Event__Group__0__Impl"


    // $ANTLR start "rule__Event__Group__1"
    // InternalIot.g:493:1: rule__Event__Group__1 : rule__Event__Group__1__Impl rule__Event__Group__2 ;
    public final void rule__Event__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalIot.g:497:1: ( rule__Event__Group__1__Impl rule__Event__Group__2 )
            // InternalIot.g:498:2: rule__Event__Group__1__Impl rule__Event__Group__2
            {
            pushFollow(FOLLOW_7);
            rule__Event__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Event__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Event__Group__1"


    // $ANTLR start "rule__Event__Group__1__Impl"
    // InternalIot.g:505:1: rule__Event__Group__1__Impl : ( ( rule__Event__NameAssignment_1 ) ) ;
    public final void rule__Event__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalIot.g:509:1: ( ( ( rule__Event__NameAssignment_1 ) ) )
            // InternalIot.g:510:1: ( ( rule__Event__NameAssignment_1 ) )
            {
            // InternalIot.g:510:1: ( ( rule__Event__NameAssignment_1 ) )
            // InternalIot.g:511:2: ( rule__Event__NameAssignment_1 )
            {
             before(grammarAccess.getEventAccess().getNameAssignment_1()); 
            // InternalIot.g:512:2: ( rule__Event__NameAssignment_1 )
            // InternalIot.g:512:3: rule__Event__NameAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__Event__NameAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getEventAccess().getNameAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Event__Group__1__Impl"


    // $ANTLR start "rule__Event__Group__2"
    // InternalIot.g:520:1: rule__Event__Group__2 : rule__Event__Group__2__Impl rule__Event__Group__3 ;
    public final void rule__Event__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalIot.g:524:1: ( rule__Event__Group__2__Impl rule__Event__Group__3 )
            // InternalIot.g:525:2: rule__Event__Group__2__Impl rule__Event__Group__3
            {
            pushFollow(FOLLOW_6);
            rule__Event__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Event__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Event__Group__2"


    // $ANTLR start "rule__Event__Group__2__Impl"
    // InternalIot.g:532:1: rule__Event__Group__2__Impl : ( ':' ) ;
    public final void rule__Event__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalIot.g:536:1: ( ( ':' ) )
            // InternalIot.g:537:1: ( ':' )
            {
            // InternalIot.g:537:1: ( ':' )
            // InternalIot.g:538:2: ':'
            {
             before(grammarAccess.getEventAccess().getColonKeyword_2()); 
            match(input,14,FOLLOW_2); 
             after(grammarAccess.getEventAccess().getColonKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Event__Group__2__Impl"


    // $ANTLR start "rule__Event__Group__3"
    // InternalIot.g:547:1: rule__Event__Group__3 : rule__Event__Group__3__Impl ;
    public final void rule__Event__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalIot.g:551:1: ( rule__Event__Group__3__Impl )
            // InternalIot.g:552:2: rule__Event__Group__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Event__Group__3__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Event__Group__3"


    // $ANTLR start "rule__Event__Group__3__Impl"
    // InternalIot.g:558:1: rule__Event__Group__3__Impl : ( ( rule__Event__MsgAssignment_3 ) ) ;
    public final void rule__Event__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalIot.g:562:1: ( ( ( rule__Event__MsgAssignment_3 ) ) )
            // InternalIot.g:563:1: ( ( rule__Event__MsgAssignment_3 ) )
            {
            // InternalIot.g:563:1: ( ( rule__Event__MsgAssignment_3 ) )
            // InternalIot.g:564:2: ( rule__Event__MsgAssignment_3 )
            {
             before(grammarAccess.getEventAccess().getMsgAssignment_3()); 
            // InternalIot.g:565:2: ( rule__Event__MsgAssignment_3 )
            // InternalIot.g:565:3: rule__Event__MsgAssignment_3
            {
            pushFollow(FOLLOW_2);
            rule__Event__MsgAssignment_3();

            state._fsp--;


            }

             after(grammarAccess.getEventAccess().getMsgAssignment_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Event__Group__3__Impl"


    // $ANTLR start "rule__Dispatch__Group__0"
    // InternalIot.g:574:1: rule__Dispatch__Group__0 : rule__Dispatch__Group__0__Impl rule__Dispatch__Group__1 ;
    public final void rule__Dispatch__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalIot.g:578:1: ( rule__Dispatch__Group__0__Impl rule__Dispatch__Group__1 )
            // InternalIot.g:579:2: rule__Dispatch__Group__0__Impl rule__Dispatch__Group__1
            {
            pushFollow(FOLLOW_3);
            rule__Dispatch__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Dispatch__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Dispatch__Group__0"


    // $ANTLR start "rule__Dispatch__Group__0__Impl"
    // InternalIot.g:586:1: rule__Dispatch__Group__0__Impl : ( 'Dispatch' ) ;
    public final void rule__Dispatch__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalIot.g:590:1: ( ( 'Dispatch' ) )
            // InternalIot.g:591:1: ( 'Dispatch' )
            {
            // InternalIot.g:591:1: ( 'Dispatch' )
            // InternalIot.g:592:2: 'Dispatch'
            {
             before(grammarAccess.getDispatchAccess().getDispatchKeyword_0()); 
            match(input,16,FOLLOW_2); 
             after(grammarAccess.getDispatchAccess().getDispatchKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Dispatch__Group__0__Impl"


    // $ANTLR start "rule__Dispatch__Group__1"
    // InternalIot.g:601:1: rule__Dispatch__Group__1 : rule__Dispatch__Group__1__Impl rule__Dispatch__Group__2 ;
    public final void rule__Dispatch__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalIot.g:605:1: ( rule__Dispatch__Group__1__Impl rule__Dispatch__Group__2 )
            // InternalIot.g:606:2: rule__Dispatch__Group__1__Impl rule__Dispatch__Group__2
            {
            pushFollow(FOLLOW_7);
            rule__Dispatch__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Dispatch__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Dispatch__Group__1"


    // $ANTLR start "rule__Dispatch__Group__1__Impl"
    // InternalIot.g:613:1: rule__Dispatch__Group__1__Impl : ( ( rule__Dispatch__NameAssignment_1 ) ) ;
    public final void rule__Dispatch__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalIot.g:617:1: ( ( ( rule__Dispatch__NameAssignment_1 ) ) )
            // InternalIot.g:618:1: ( ( rule__Dispatch__NameAssignment_1 ) )
            {
            // InternalIot.g:618:1: ( ( rule__Dispatch__NameAssignment_1 ) )
            // InternalIot.g:619:2: ( rule__Dispatch__NameAssignment_1 )
            {
             before(grammarAccess.getDispatchAccess().getNameAssignment_1()); 
            // InternalIot.g:620:2: ( rule__Dispatch__NameAssignment_1 )
            // InternalIot.g:620:3: rule__Dispatch__NameAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__Dispatch__NameAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getDispatchAccess().getNameAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Dispatch__Group__1__Impl"


    // $ANTLR start "rule__Dispatch__Group__2"
    // InternalIot.g:628:1: rule__Dispatch__Group__2 : rule__Dispatch__Group__2__Impl rule__Dispatch__Group__3 ;
    public final void rule__Dispatch__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalIot.g:632:1: ( rule__Dispatch__Group__2__Impl rule__Dispatch__Group__3 )
            // InternalIot.g:633:2: rule__Dispatch__Group__2__Impl rule__Dispatch__Group__3
            {
            pushFollow(FOLLOW_6);
            rule__Dispatch__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Dispatch__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Dispatch__Group__2"


    // $ANTLR start "rule__Dispatch__Group__2__Impl"
    // InternalIot.g:640:1: rule__Dispatch__Group__2__Impl : ( ':' ) ;
    public final void rule__Dispatch__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalIot.g:644:1: ( ( ':' ) )
            // InternalIot.g:645:1: ( ':' )
            {
            // InternalIot.g:645:1: ( ':' )
            // InternalIot.g:646:2: ':'
            {
             before(grammarAccess.getDispatchAccess().getColonKeyword_2()); 
            match(input,14,FOLLOW_2); 
             after(grammarAccess.getDispatchAccess().getColonKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Dispatch__Group__2__Impl"


    // $ANTLR start "rule__Dispatch__Group__3"
    // InternalIot.g:655:1: rule__Dispatch__Group__3 : rule__Dispatch__Group__3__Impl ;
    public final void rule__Dispatch__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalIot.g:659:1: ( rule__Dispatch__Group__3__Impl )
            // InternalIot.g:660:2: rule__Dispatch__Group__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Dispatch__Group__3__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Dispatch__Group__3"


    // $ANTLR start "rule__Dispatch__Group__3__Impl"
    // InternalIot.g:666:1: rule__Dispatch__Group__3__Impl : ( ( rule__Dispatch__MsgAssignment_3 ) ) ;
    public final void rule__Dispatch__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalIot.g:670:1: ( ( ( rule__Dispatch__MsgAssignment_3 ) ) )
            // InternalIot.g:671:1: ( ( rule__Dispatch__MsgAssignment_3 ) )
            {
            // InternalIot.g:671:1: ( ( rule__Dispatch__MsgAssignment_3 ) )
            // InternalIot.g:672:2: ( rule__Dispatch__MsgAssignment_3 )
            {
             before(grammarAccess.getDispatchAccess().getMsgAssignment_3()); 
            // InternalIot.g:673:2: ( rule__Dispatch__MsgAssignment_3 )
            // InternalIot.g:673:3: rule__Dispatch__MsgAssignment_3
            {
            pushFollow(FOLLOW_2);
            rule__Dispatch__MsgAssignment_3();

            state._fsp--;


            }

             after(grammarAccess.getDispatchAccess().getMsgAssignment_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Dispatch__Group__3__Impl"


    // $ANTLR start "rule__IotSystem__SpecAssignment_1"
    // InternalIot.g:682:1: rule__IotSystem__SpecAssignment_1 : ( ruleIotSystemSpec ) ;
    public final void rule__IotSystem__SpecAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalIot.g:686:1: ( ( ruleIotSystemSpec ) )
            // InternalIot.g:687:2: ( ruleIotSystemSpec )
            {
            // InternalIot.g:687:2: ( ruleIotSystemSpec )
            // InternalIot.g:688:3: ruleIotSystemSpec
            {
             before(grammarAccess.getIotSystemAccess().getSpecIotSystemSpecParserRuleCall_1_0()); 
            pushFollow(FOLLOW_2);
            ruleIotSystemSpec();

            state._fsp--;

             after(grammarAccess.getIotSystemAccess().getSpecIotSystemSpecParserRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IotSystem__SpecAssignment_1"


    // $ANTLR start "rule__IotSystemSpec__NameAssignment_0"
    // InternalIot.g:697:1: rule__IotSystemSpec__NameAssignment_0 : ( RULE_ID ) ;
    public final void rule__IotSystemSpec__NameAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalIot.g:701:1: ( ( RULE_ID ) )
            // InternalIot.g:702:2: ( RULE_ID )
            {
            // InternalIot.g:702:2: ( RULE_ID )
            // InternalIot.g:703:3: RULE_ID
            {
             before(grammarAccess.getIotSystemSpecAccess().getNameIDTerminalRuleCall_0_0()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getIotSystemSpecAccess().getNameIDTerminalRuleCall_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IotSystemSpec__NameAssignment_0"


    // $ANTLR start "rule__IotSystemSpec__MqttBrokerAssignment_1"
    // InternalIot.g:712:1: rule__IotSystemSpec__MqttBrokerAssignment_1 : ( ruleBrokerSpec ) ;
    public final void rule__IotSystemSpec__MqttBrokerAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalIot.g:716:1: ( ( ruleBrokerSpec ) )
            // InternalIot.g:717:2: ( ruleBrokerSpec )
            {
            // InternalIot.g:717:2: ( ruleBrokerSpec )
            // InternalIot.g:718:3: ruleBrokerSpec
            {
             before(grammarAccess.getIotSystemSpecAccess().getMqttBrokerBrokerSpecParserRuleCall_1_0()); 
            pushFollow(FOLLOW_2);
            ruleBrokerSpec();

            state._fsp--;

             after(grammarAccess.getIotSystemSpecAccess().getMqttBrokerBrokerSpecParserRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IotSystemSpec__MqttBrokerAssignment_1"


    // $ANTLR start "rule__IotSystemSpec__MessageAssignment_2"
    // InternalIot.g:727:1: rule__IotSystemSpec__MessageAssignment_2 : ( ruleMessage ) ;
    public final void rule__IotSystemSpec__MessageAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalIot.g:731:1: ( ( ruleMessage ) )
            // InternalIot.g:732:2: ( ruleMessage )
            {
            // InternalIot.g:732:2: ( ruleMessage )
            // InternalIot.g:733:3: ruleMessage
            {
             before(grammarAccess.getIotSystemSpecAccess().getMessageMessageParserRuleCall_2_0()); 
            pushFollow(FOLLOW_2);
            ruleMessage();

            state._fsp--;

             after(grammarAccess.getIotSystemSpecAccess().getMessageMessageParserRuleCall_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IotSystemSpec__MessageAssignment_2"


    // $ANTLR start "rule__BrokerSpec__BrokerHostAssignment_1"
    // InternalIot.g:742:1: rule__BrokerSpec__BrokerHostAssignment_1 : ( RULE_STRING ) ;
    public final void rule__BrokerSpec__BrokerHostAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalIot.g:746:1: ( ( RULE_STRING ) )
            // InternalIot.g:747:2: ( RULE_STRING )
            {
            // InternalIot.g:747:2: ( RULE_STRING )
            // InternalIot.g:748:3: RULE_STRING
            {
             before(grammarAccess.getBrokerSpecAccess().getBrokerHostSTRINGTerminalRuleCall_1_0()); 
            match(input,RULE_STRING,FOLLOW_2); 
             after(grammarAccess.getBrokerSpecAccess().getBrokerHostSTRINGTerminalRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__BrokerSpec__BrokerHostAssignment_1"


    // $ANTLR start "rule__BrokerSpec__BrokerPortAssignment_3"
    // InternalIot.g:757:1: rule__BrokerSpec__BrokerPortAssignment_3 : ( RULE_INT ) ;
    public final void rule__BrokerSpec__BrokerPortAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalIot.g:761:1: ( ( RULE_INT ) )
            // InternalIot.g:762:2: ( RULE_INT )
            {
            // InternalIot.g:762:2: ( RULE_INT )
            // InternalIot.g:763:3: RULE_INT
            {
             before(grammarAccess.getBrokerSpecAccess().getBrokerPortINTTerminalRuleCall_3_0()); 
            match(input,RULE_INT,FOLLOW_2); 
             after(grammarAccess.getBrokerSpecAccess().getBrokerPortINTTerminalRuleCall_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__BrokerSpec__BrokerPortAssignment_3"


    // $ANTLR start "rule__Event__NameAssignment_1"
    // InternalIot.g:772:1: rule__Event__NameAssignment_1 : ( RULE_ID ) ;
    public final void rule__Event__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalIot.g:776:1: ( ( RULE_ID ) )
            // InternalIot.g:777:2: ( RULE_ID )
            {
            // InternalIot.g:777:2: ( RULE_ID )
            // InternalIot.g:778:3: RULE_ID
            {
             before(grammarAccess.getEventAccess().getNameIDTerminalRuleCall_1_0()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getEventAccess().getNameIDTerminalRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Event__NameAssignment_1"


    // $ANTLR start "rule__Event__MsgAssignment_3"
    // InternalIot.g:787:1: rule__Event__MsgAssignment_3 : ( RULE_STRING ) ;
    public final void rule__Event__MsgAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalIot.g:791:1: ( ( RULE_STRING ) )
            // InternalIot.g:792:2: ( RULE_STRING )
            {
            // InternalIot.g:792:2: ( RULE_STRING )
            // InternalIot.g:793:3: RULE_STRING
            {
             before(grammarAccess.getEventAccess().getMsgSTRINGTerminalRuleCall_3_0()); 
            match(input,RULE_STRING,FOLLOW_2); 
             after(grammarAccess.getEventAccess().getMsgSTRINGTerminalRuleCall_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Event__MsgAssignment_3"


    // $ANTLR start "rule__Dispatch__NameAssignment_1"
    // InternalIot.g:802:1: rule__Dispatch__NameAssignment_1 : ( RULE_ID ) ;
    public final void rule__Dispatch__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalIot.g:806:1: ( ( RULE_ID ) )
            // InternalIot.g:807:2: ( RULE_ID )
            {
            // InternalIot.g:807:2: ( RULE_ID )
            // InternalIot.g:808:3: RULE_ID
            {
             before(grammarAccess.getDispatchAccess().getNameIDTerminalRuleCall_1_0()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getDispatchAccess().getNameIDTerminalRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Dispatch__NameAssignment_1"


    // $ANTLR start "rule__Dispatch__MsgAssignment_3"
    // InternalIot.g:817:1: rule__Dispatch__MsgAssignment_3 : ( RULE_STRING ) ;
    public final void rule__Dispatch__MsgAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalIot.g:821:1: ( ( RULE_STRING ) )
            // InternalIot.g:822:2: ( RULE_STRING )
            {
            // InternalIot.g:822:2: ( RULE_STRING )
            // InternalIot.g:823:3: RULE_STRING
            {
             before(grammarAccess.getDispatchAccess().getMsgSTRINGTerminalRuleCall_3_0()); 
            match(input,RULE_STRING,FOLLOW_2); 
             after(grammarAccess.getDispatchAccess().getMsgSTRINGTerminalRuleCall_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Dispatch__MsgAssignment_3"

    // Delegated rules


 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x000000000001A000L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000000018002L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000000000040L});

}