import {RichTextEditor, Link} from '@mantine/tiptap';
import {useEditor} from '@tiptap/react';
import Highlight from '@tiptap/extension-highlight';
import StarterKit from '@tiptap/starter-kit';
import Underline from '@tiptap/extension-underline';
import TextAlign from '@tiptap/extension-text-align';
import Superscript from '@tiptap/extension-superscript';
import SubScript from '@tiptap/extension-subscript';


import Collaboration from '@tiptap/extension-collaboration'
import * as Y from 'yjs'
import { TiptapCollabProvider } from '@hocuspocus/provider'
import {CollaborationCursor} from "@tiptap/extension-collaboration-cursor";
import {IndexeddbPersistence} from "y-indexeddb";

const doc = new Y.Doc() // Initialize Y.Doc for shared editing
// Set up IndexedDB for local storage of the Y document
new IndexeddbPersistence('example-document', doc)

import { WebrtcProvider } from 'y-webrtc'



const users = [
    { name: 'Leah Nug', color: 'rgba(239, 207, 227, 1)'},
    { name: 'John Doe', color: 'rgba(239, 207, 227, 1)' },
];

const tokens = [
    "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3MjU3NzMwODEsIm5iZiI6MTcyNTc3MzA4MSwiZXhwIjoxNzI1ODU5NDgxLCJpc3MiOiJodHRwczovL2Nsb3VkLnRpcHRhcC5kZXYiLCJhdWQiOiJ4OWxncHprciJ9.QsAH0zN_ZA1kFdD7M-FmpXPTYevV9lpY1uo3dfmabKw",
    "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3MjU3NzMxMTQsIm5iZiI6MTcyNTc3MzExNCwiZXhwIjoxNzI1ODU5NTE0LCJpc3MiOiJodHRwczovL2Nsb3VkLnRpcHRhcC5kZXYiLCJhdWQiOiJ4OWxncHprciJ9.vCdocf9hPZt3441v-uYOXdjZZa6kV_6_j-BgoM0O3b4"
];

// Function to randomly select a user from the list
function getRandomUser() {
    const randomIndex = Math.floor(Math.random() * users.length);
    return users[randomIndex];
}
// function getRandomToken() {
//     const randomIndex = Math.floor(Math.random() * tokens.length);
//     return tokens[randomIndex];
// }

const provider = new WebrtcProvider('test', doc)


const content =
    '<h2 style="text-align: center;">Welcome to Mantine rich text editor</h2><p><code>RichTextEditor</code> component focuses on usability and is designed to be as simple as possible to bring a familiar editing experience to regular users. <code>RichTextEditor</code> is based on <a href="https://tiptap.dev/" rel="noopener noreferrer" target="_blank">Tiptap.dev</a> and supports all of its features:</p><ul><li>General text formatting: <strong>bold</strong>, <em>italic</em>, <u>underline</u>, <s>strike-through</s> </li><li>Headings (h1-h6)</li><li>Sub and super scripts (<sup>&lt;sup /&gt;</sup> and <sub>&lt;sub /&gt;</sub> tags)</li><li>Ordered and bullet lists</li><li>Text align&nbsp;</li><li>And all <a href="https://tiptap.dev/extensions" target="_blank" rel="noopener noreferrer">other extensions</a></li></ul>';

const AppRichTextEditor: React.FC = () => {

    // useEffect(() => {
    //     const newProvider = new TiptapCollabProvider({
    //         name: 'document.name', // Unique document identifier for syncing. This is your document name.
    //         appId: 'x9lgpzkr', // Your Cloud Dashboard AppID or `baseURL` for on-premises
    //         token: getRandomToken(), // Your JWT token
    //         document: doc,
    //         onOpen() {
    //             console.log('WebSocket connection opened.');
    //         },
    //         onConnect() {
    //             console.log('Connected to the server.');
    //         },
    //     });
    //
    //     setProvider(newProvider);
    // }, []);

// Build the extensions array dynamically
    const extensions = [
        StarterKit,
        Underline,
        Link,
        Superscript,
        SubScript,
        Highlight,
        Collaboration.configure({
            document: doc, // Configure Y.Doc for collaboration
        }),
        CollaborationCursor.configure({
            provider,
            user: getRandomUser(),
        }),
        TextAlign.configure({ types: ['heading', 'paragraph'] }),
    ];



    const editor = useEditor({
        extensions,
        content,
    });

    return (
        <RichTextEditor  editor={editor}>
            <RichTextEditor.Toolbar sticky stickyOffset={30}>
                <RichTextEditor.ControlsGroup>
                    <RichTextEditor.Bold />
                    <RichTextEditor.Italic />
                    <RichTextEditor.Underline />
                    <RichTextEditor.Strikethrough />
                    <RichTextEditor.ClearFormatting />
                    <RichTextEditor.Highlight />
                    <RichTextEditor.Code />
                </RichTextEditor.ControlsGroup>

                <RichTextEditor.ControlsGroup>
                    <RichTextEditor.H1 />
                    <RichTextEditor.H2 />
                    <RichTextEditor.H3 />
                    <RichTextEditor.H4 />
                </RichTextEditor.ControlsGroup>

                <RichTextEditor.ControlsGroup>
                    <RichTextEditor.Blockquote />
                    <RichTextEditor.Hr />
                    <RichTextEditor.BulletList />
                    <RichTextEditor.OrderedList />
                    <RichTextEditor.Subscript />
                    <RichTextEditor.Superscript />
                </RichTextEditor.ControlsGroup>

                <RichTextEditor.ControlsGroup>
                    <RichTextEditor.Link />
                    <RichTextEditor.Unlink />
                </RichTextEditor.ControlsGroup>

                <RichTextEditor.ControlsGroup>
                    <RichTextEditor.AlignLeft />
                    <RichTextEditor.AlignCenter />
                    <RichTextEditor.AlignJustify />
                    <RichTextEditor.AlignRight />
                </RichTextEditor.ControlsGroup>

                <RichTextEditor.ControlsGroup>
                    <RichTextEditor.Undo />
                    <RichTextEditor.Redo />
                </RichTextEditor.ControlsGroup>
            </RichTextEditor.Toolbar>

            <RichTextEditor.Content />
        </RichTextEditor >
    );
};
export default AppRichTextEditor;