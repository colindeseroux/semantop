--
-- PostgreSQL database dump
--

-- Dumped from database version 17.5 (Debian 17.5-1.pgdg120+1)
-- Dumped by pg_dump version 17.5 (Debian 17.5-1.pgdg120+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: email_templates; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.email_templates (
    id bigint NOT NULL,
    content text NOT NULL,
    name character varying(255) NOT NULL,
    subject character varying(255) NOT NULL
);


ALTER TABLE public.email_templates OWNER TO postgres;

--
-- Name: email_templates_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.email_templates_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.email_templates_seq OWNER TO postgres;

--
-- Name: refs; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.refs (
    id bigint NOT NULL,
    ref character varying(255) NOT NULL,
    word_id bigint NOT NULL
);


ALTER TABLE public.refs OWNER TO postgres;

--
-- Name: refs_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.refs_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.refs_seq OWNER TO postgres;

--
-- Name: user_refs; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_refs (
    id bigint NOT NULL,
    win boolean NOT NULL,
    ref_id bigint NOT NULL,
    user_id bigint NOT NULL
);


ALTER TABLE public.user_refs OWNER TO postgres;

--
-- Name: user_refs_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.user_refs_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.user_refs_seq OWNER TO postgres;

--
-- Name: user_word_search; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_word_search (
    user_ref_id bigint NOT NULL,
    word_search_id bigint NOT NULL
);


ALTER TABLE public.user_word_search OWNER TO postgres;

--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id bigint NOT NULL,
    email character varying(255) NOT NULL,
    login_attempts integer NOT NULL,
    password character varying(255) NOT NULL,
    pseudo character varying(255) NOT NULL
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Name: users_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.users_seq OWNER TO postgres;

--
-- Name: verification_codes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.verification_codes (
    id bigint NOT NULL,
    code character varying(255) NOT NULL,
    email character varying(255) NOT NULL,
    expiration_date timestamp(6) without time zone NOT NULL
);


ALTER TABLE public.verification_codes OWNER TO postgres;

--
-- Name: verification_codes_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.verification_codes_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.verification_codes_seq OWNER TO postgres;

--
-- Name: word_searchs; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.word_searchs (
    id bigint NOT NULL,
    degree double precision NOT NULL,
    place integer NOT NULL,
    word character varying(255)
);


ALTER TABLE public.word_searchs OWNER TO postgres;

--
-- Name: word_searchs_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.word_searchs_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.word_searchs_seq OWNER TO postgres;

--
-- Name: words; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.words (
    id bigint NOT NULL,
    bonus integer NOT NULL,
    word character varying(255) NOT NULL
);


ALTER TABLE public.words OWNER TO postgres;

--
-- Name: words_downvoted; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.words_downvoted (
    word_id bigint NOT NULL,
    user_id bigint NOT NULL
);


ALTER TABLE public.words_downvoted OWNER TO postgres;

--
-- Name: words_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.words_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.words_seq OWNER TO postgres;

--
-- Name: words_upvoted; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.words_upvoted (
    word_id bigint NOT NULL,
    user_id bigint NOT NULL
);


ALTER TABLE public.words_upvoted OWNER TO postgres;

--
-- Data for Name: email_templates; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.email_templates (id, content, name, subject) FROM stdin;
1	<html lang="fr">\r\n  <head>\r\n    <meta charset="UTF-8" />\r\n    <meta name="viewport" content="width=device-width, initial-scale=1.0" />\r\n    <title>Password forgotten</title>\r\n  </head>\r\n  <body\r\n    style="\r\n      margin: 20px;\r\n      padding: 0;\r\n      font-family: Arial, sans-serif;\r\n      background-color: none;\r\n    "\r\n  >\r\n    <!-- Body -->\r\n    <div\r\n      style="\r\n        width: 100%;\r\n        max-width: 600px;\r\n        margin: auto;\r\n        background-color: #ffffff;\r\n        border-radius: 15px;\r\n        overflow: hidden;\r\n        border: 1px solid #d3d3d3;\r\n        text-align: center;\r\n      "\r\n    >\r\n      <!-- Header -->\r\n      <header style="background-color: #002d5e; padding: 1px">\r\n        <img\r\n          src="https://upload.wikimedia.org/wikipedia/commons/thumb/f/f3/Rdf_logo.svg/768px-Rdf_logo.svg.png"\r\n          alt="RDF logo"\r\n          style="\r\n            width: 100px;\r\n            max-width: 100%;\r\n            height: auto;\r\n            display: block;\r\n            margin: 5px auto;\r\n          "\r\n        />\r\n      </header>\r\n\r\n      <!-- Content -->\r\n      <div\r\n        style="padding: 20px; color: #555; font-size: 16px; line-height: 1.6"\r\n      >\r\n        <h1 style="color: #3f4d75; margin-bottom: 20px">\r\n          PASSWORD FORGOTTEN ?\r\n        </h1>\r\n\r\n        <p>No worries !</p>\r\n\r\n        <img\r\n          src="https://d1oco4z2z1fhwp.cloudfront.net/templates/default/3856/GIF_password.gif"\r\n          alt="Wrong Password Animation"\r\n          style="\r\n            max-width: 100%;\r\n            height: auto;\r\n            display: block;\r\n            margin: 20px auto;\r\n            border-radius: 10px;\r\n          "\r\n        />\r\n\r\n        <h1 style="color: #3f4d75">Here is your verification code</h1>\r\n\r\n        <span\r\n          style="\r\n            color: #3f4d75;\r\n            font-size: 30px;\r\n            font-weight: bold;\r\n            letter-spacing: 5px;\r\n          "\r\n          th:text="${code}"\r\n        >\r\n          123456\r\n        </span>\r\n\r\n      <!-- Divider -->\r\n      <hr style="border: none; border-top: 1px solid #d3d3d3; margin: 0 20px" />\r\n\r\n      <!-- Footer -->\r\n      <footer style="padding: 0 20px; font-size: 14px; color: #888">\r\n        <p>\r\n          If you have any questions, please do not hesitate to contact us at\r\n          <a\r\n            href="mailto:contact@semantop.colindeseroux.fr"\r\n            style="color: #002d5e; text-decoration: none"\r\n            >contact@semantop.colindeseroux.fr</a\r\n          >.\r\n        </p>\r\n        <p style="font-size: 12px; margin-top: 10px">\r\n          &copy; 2025 Colin de Seroux. All rights reserved.\r\n        </p>\r\n      </footer>\r\n    </div>\r\n  </body>\r\n</html>	password_forgotten	Password forgotten
2	<html lang="fr">\r\n  <head>\r\n    <meta charset="UTF-8" />\r\n    <meta name="viewport" content="width=device-width, initial-scale=1.0" />\r\n    <title>Verification code expired</title>\r\n  </head>\r\n  <body\r\n    style="\r\n      margin: 20px;\r\n      padding: 0;\r\n      font-family: Arial, sans-serif;\r\n      background-color: none;\r\n    "\r\n  >\r\n    <!-- Body -->\r\n    <div\r\n      style="\r\n        width: 100%;\r\n        max-width: 600px;\r\n        margin: auto;\r\n        background-color: #ffffff;\r\n        border-radius: 15px;\r\n        overflow: hidden;\r\n        border: 1px solid #d3d3d3;\r\n        text-align: center;\r\n      "\r\n    >\r\n      <!-- Header -->\r\n      <header style="background-color: #002d5e; padding: 1px">\r\n        <img\r\n          src="https://upload.wikimedia.org/wikipedia/commons/thumb/f/f3/Rdf_logo.svg/768px-Rdf_logo.svg.png"\r\n          alt="RDF logo"\r\n          style="\r\n            width: 100px;\r\n            max-width: 100%;\r\n            height: auto;\r\n            display: block;\r\n            margin: 5px auto;\r\n          "\r\n        />\r\n      </header>\r\n\r\n      <!-- Content -->\r\n      <div\r\n        style="padding: 20px; color: #555; font-size: 16px; line-height: 1.6"\r\n      >\r\n        <h1 style="color: #3f4d75; margin-bottom: 20px">\r\n          OOPS, YOUR VERIFICATION CODE HAS EXPIRED\r\n        </h1>\r\n\r\n        <p>Pas de soucis !</p>\r\n\r\n        <img\r\n          src="https://d1oco4z2z1fhwp.cloudfront.net/templates/default/3856/GIF_password.gif"\r\n          alt="Wrong Password Animation"\r\n          style="\r\n            max-width: 100%;\r\n            height: auto;\r\n            display: block;\r\n            margin: 20px auto;\r\n            border-radius: 10px;\r\n          "\r\n        />\r\n\r\n        <h1 style="color: #3f4d75">Here is a new verification code</h1>\r\n\r\n        <span\r\n          style="\r\n            color: #3f4d75;\r\n            font-size: 30px;\r\n            font-weight: bold;\r\n            letter-spacing: 5px;\r\n          "\r\n          th:text="${code}"\r\n        >\r\n          123456\r\n        </span>\r\n\r\n        <p style="font-size: 12px; color: #888">\r\n          This code will expire in 1 hour.\r\n        </p>\r\n      </div>\r\n\r\n      <!-- Divider -->\r\n      <hr style="border: none; border-top: 1px solid #d3d3d3; margin: 0 20px" />\r\n\r\n      <!-- Footer -->\r\n      <footer style="padding: 0 20px; font-size: 14px; color: #888">\r\n        <p>\r\n          If you have any questions, please do not hesitate to contact us at\r\n          <a\r\n            href="mailto:contact@semantop.colindeseroux.fr"\r\n            style="color: #002d5e; text-decoration: none"\r\n            >contact@semantop.colindeseroux.fr</a\r\n          >.\r\n        </p>\r\n        <p style="font-size: 12px; margin-top: 10px">\r\n          &copy; 2025 Colin de Seroux. All rights reserved.\r\n        </p>\r\n      </footer>\r\n    </div>\r\n  </body>\r\n</html>	verification_code_expired	Verification code expired
4	<html lang="fr">\r\n  <head>\r\n    <meta charset="UTF-8" />\r\n    <meta name="viewport" content="width=device-width, initial-scale=1.0" />\r\n    <title>New account</title>\r\n  </head>\r\n  <body\r\n    style="\r\n      margin: 20px;\r\n      padding: 0;\r\n      font-family: Arial, sans-serif;\r\n      background-color: none;\r\n    "\r\n  >\r\n    <!-- Body -->\r\n    <div\r\n      style="\r\n        width: 100%;\r\n        max-width: 600px;\r\n        margin: auto;\r\n        background-color: #ffffff;\r\n        border-radius: 15px;\r\n        overflow: hidden;\r\n        border: 1px solid #d3d3d3;\r\n        text-align: center;\r\n      "\r\n    >\r\n      <!-- Header -->\r\n      <header style="background-color: #002d5e; padding: 1px">\r\n        <img\r\n          src="https://upload.wikimedia.org/wikipedia/commons/thumb/f/f3/Rdf_logo.svg/768px-Rdf_logo.svg.png"\r\n          alt="RDF logo"\r\n          style="\r\n            width: 100px;\r\n            max-width: 100%;\r\n            height: auto;\r\n            display: block;\r\n            margin: 5px auto;\r\n          "\r\n        />\r\n      </header>\r\n\r\n      <!-- Content -->\r\n      <div\r\n        style="padding: 20px; color: #555; font-size: 16px; line-height: 1.6"\r\n      >\r\n        <h1 style="color: #3f4d75; margin-bottom: 20px">\r\n          WELCOME TO OUR PLATFORM\r\n        </h1>\r\n\r\n        <p>\r\n          Welcome\r\n          <strong>\r\n            <span th:text="${pseudo}">Reboot333</span>\r\n          </strong>\r\n        </p>\r\n\r\n        <p>\r\n          Thank you for joining us. Below is the link to verify your account.\r\n        </p>\r\n\r\n        <!-- Link -->\r\n        <div style="margin: 30px 0">\r\n          <a\r\n            th:href="${apiBaseUrl + &#39;/auth/validate?email=&#39; + email + &#39;&code=&#39; + code}"\r\n            style="\r\n              background-color: #002d5e;\r\n              color: #ffffff;\r\n              padding: 12px 30px;\r\n              border-radius: 5px;\r\n              text-decoration: none;\r\n              font-weight: bold;\r\n            "\r\n            >NEXT</a\r\n          >\r\n        </div>\r\n\r\n        <p style="font-size: 12px; color: #888">\r\n          This link will expire in 24 hours.\r\n        </p>\r\n      </div>\r\n\r\n      <!-- Divider -->\r\n      <hr style="border: none; border-top: 1px solid #d3d3d3; margin: 0 20px" />\r\n\r\n      <!-- Footer -->\r\n      <footer style="padding: 0 20px; font-size: 14px; color: #888">\r\n        <p>\r\n          If you have any questions, please do not hesitate to contact us at\r\n          <a\r\n            href="mailto:contact@semantop.colindeseroux.fr"\r\n            style="color: #002d5e; text-decoration: none"\r\n            >contact@semantop.colindeseroux.fr</a\r\n          >.\r\n        </p>\r\n        <p style="font-size: 12px; margin-top: 10px">\r\n          &copy; 2025 Colin de Seroux. All rights reserved.\r\n        </p>\r\n      </footer>\r\n    </div>\r\n  </body>\r\n</html>	new_account	New account
\.


--
-- Data for Name: refs; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.refs (id, ref, word_id) FROM stdin;
\.


--
-- Data for Name: user_refs; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_refs (id, win, ref_id, user_id) FROM stdin;
\.


--
-- Data for Name: user_word_search; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_word_search (user_ref_id, word_search_id) FROM stdin;
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, email, login_attempts, password, pseudo) FROM stdin;
\.


--
-- Data for Name: verification_codes; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.verification_codes (id, code, email, expiration_date) FROM stdin;
\.


--
-- Data for Name: word_searchs; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.word_searchs (id, degree, place, word) FROM stdin;
\.


--
-- Data for Name: words; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.words (id, bonus, word) FROM stdin;
\.


--
-- Data for Name: words_downvoted; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.words_downvoted (word_id, user_id) FROM stdin;
\.


--
-- Data for Name: words_upvoted; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.words_upvoted (word_id, user_id) FROM stdin;
\.


--
-- Name: email_templates_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.email_templates_seq', 1, false);


--
-- Name: refs_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.refs_seq', 1, false);


--
-- Name: user_refs_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.user_refs_seq', 1, false);


--
-- Name: users_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_seq', 1, false);


--
-- Name: verification_codes_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.verification_codes_seq', 1, false);


--
-- Name: word_searchs_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.word_searchs_seq', 1, false);


--
-- Name: words_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.words_seq', 1, false);


--
-- Name: email_templates email_templates_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.email_templates
    ADD CONSTRAINT email_templates_pkey PRIMARY KEY (id);


--
-- Name: refs refs_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.refs
    ADD CONSTRAINT refs_pkey PRIMARY KEY (id);


--
-- Name: words uk4tnfb8fuowge5ujwsnqb5arls; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.words
    ADD CONSTRAINT uk4tnfb8fuowge5ujwsnqb5arls UNIQUE (word);


--
-- Name: email_templates uk57lojflgusywyihcoxex20bml; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.email_templates
    ADD CONSTRAINT uk57lojflgusywyihcoxex20bml UNIQUE (name);


--
-- Name: users uk6dotkott2kjsp8vw4d0m25fb7; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email);


--
-- Name: refs ukbw52s0tlkdncv60rt1y1t0h20; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.refs
    ADD CONSTRAINT ukbw52s0tlkdncv60rt1y1t0h20 UNIQUE (ref);


--
-- Name: verification_codes ukg6p4erxfa9j8bkiu2x3id5hrg; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.verification_codes
    ADD CONSTRAINT ukg6p4erxfa9j8bkiu2x3id5hrg UNIQUE (email);


--
-- Name: users ukr9i2upm423j62a0neosbc8ucq; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT ukr9i2upm423j62a0neosbc8ucq UNIQUE (pseudo);


--
-- Name: user_refs user_refs_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_refs
    ADD CONSTRAINT user_refs_pkey PRIMARY KEY (id);


--
-- Name: user_word_search user_word_search_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_word_search
    ADD CONSTRAINT user_word_search_pkey PRIMARY KEY (user_ref_id, word_search_id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: verification_codes verification_codes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.verification_codes
    ADD CONSTRAINT verification_codes_pkey PRIMARY KEY (id);


--
-- Name: word_searchs word_searchs_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.word_searchs
    ADD CONSTRAINT word_searchs_pkey PRIMARY KEY (id);


--
-- Name: words_downvoted words_downvoted_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.words_downvoted
    ADD CONSTRAINT words_downvoted_pkey PRIMARY KEY (word_id, user_id);


--
-- Name: words words_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.words
    ADD CONSTRAINT words_pkey PRIMARY KEY (id);


--
-- Name: words_upvoted words_upvoted_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.words_upvoted
    ADD CONSTRAINT words_upvoted_pkey PRIMARY KEY (word_id, user_id);


--
-- Name: idx6dotkott2kjsp8vw4d0m25fb7; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx6dotkott2kjsp8vw4d0m25fb7 ON public.users USING btree (email);


--
-- Name: idxr9i2upm423j62a0neosbc8ucq; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idxr9i2upm423j62a0neosbc8ucq ON public.users USING btree (pseudo);


--
-- Name: words_downvoted fk44pevdgxjuhph133a9m3wakv3; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.words_downvoted
    ADD CONSTRAINT fk44pevdgxjuhph133a9m3wakv3 FOREIGN KEY (word_id) REFERENCES public.words(id);


--
-- Name: user_word_search fk9rjoxw2due3qtc3y69d2mgmu9; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_word_search
    ADD CONSTRAINT fk9rjoxw2due3qtc3y69d2mgmu9 FOREIGN KEY (word_search_id) REFERENCES public.word_searchs(id);


--
-- Name: user_refs fkbcmgxd7tsx6rnu8lnhejc4ud9; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_refs
    ADD CONSTRAINT fkbcmgxd7tsx6rnu8lnhejc4ud9 FOREIGN KEY (ref_id) REFERENCES public.refs(id);


--
-- Name: user_word_search fkec4ucmm4txjo69cc9jwh2rld2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_word_search
    ADD CONSTRAINT fkec4ucmm4txjo69cc9jwh2rld2 FOREIGN KEY (user_ref_id) REFERENCES public.user_refs(id);


--
-- Name: words_upvoted fkev0n078fq0jpbv6agx7dmr2i2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.words_upvoted
    ADD CONSTRAINT fkev0n078fq0jpbv6agx7dmr2i2 FOREIGN KEY (word_id) REFERENCES public.words(id);


--
-- Name: refs fko7loqh86k0b822t7vk7wd1w1a; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.refs
    ADD CONSTRAINT fko7loqh86k0b822t7vk7wd1w1a FOREIGN KEY (word_id) REFERENCES public.words(id);


--
-- Name: words_upvoted fkrkm8xaogx04ctmefd068qd5jc; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.words_upvoted
    ADD CONSTRAINT fkrkm8xaogx04ctmefd068qd5jc FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: user_refs fkrl0c7bjhk3iu6w18il63mq7jr; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_refs
    ADD CONSTRAINT fkrl0c7bjhk3iu6w18il63mq7jr FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: words_downvoted fkrqcn7tjwxdyhx1johbfney7v7; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.words_downvoted
    ADD CONSTRAINT fkrqcn7tjwxdyhx1johbfney7v7 FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- PostgreSQL database dump complete
--

