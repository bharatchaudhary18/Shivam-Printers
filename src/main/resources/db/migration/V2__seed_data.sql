-- Seed roles
INSERT INTO roles (name, description, created_timestamp, updated_timestamp) VALUES
('ADMIN', 'Administrator with full access', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('CUSTOMER', 'Customer with dashboard access', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Seed services
INSERT INTO services (name, slug, description, short_description, icon, base_price, featured, sort_order, active, created_timestamp, updated_timestamp) VALUES
('Graphic Design', 'graphic-design', 'Professional graphic design for all your branding needs.', 'Creative visual solutions for modern brands.', 'palette', 2999.00, true, 1, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Logo Design', 'logo-design', 'Memorable logo designs that define your brand identity.', 'Distinctive logos crafted with precision.', 'sparkles', 4999.00, true, 2, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Banner Design', 'banner-design', 'Eye-catching banners for events, shops, and promotions.', 'High-impact banner designs.', 'flag', 1499.00, true, 3, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Flex Printing', 'flex-printing', 'Premium flex printing for hoardings and signage.', 'Durable flex prints with vivid colors.', 'printer', 899.00, false, 4, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Visiting Cards', 'visiting-cards', 'Elegant visiting cards with premium finishes.', 'Make a lasting first impression.', 'id-card', 599.00, true, 5, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Wedding Cards', 'wedding-cards', 'Luxurious wedding invitation cards and stationery.', 'Celebrate your special day in style.', 'heart', 3999.00, true, 6, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Brochure Design', 'brochure-design', 'Professional brochures that tell your brand story.', 'Informative and visually stunning brochures.', 'book-open', 2499.00, false, 7, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Packaging Design', 'packaging-design', 'Creative packaging that stands out on shelves.', 'Product packaging that sells.', 'package', 5999.00, false, 8, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Digital Printing', 'digital-printing', 'Fast, high-quality digital printing services.', 'Quick turnaround digital prints.', 'monitor', 499.00, false, 9, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Offset Printing', 'offset-printing', 'Large volume offset printing with superior quality.', 'Cost-effective bulk printing.', 'layers', 1999.00, false, 10, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Social Media Post Design', 'social-media-post-design', 'Engaging social media creatives for all platforms.', 'Scroll-stopping social content.', 'share-2', 999.00, true, 11, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Custom Printing', 'custom-printing', 'Fully customized printing solutions for unique requirements.', 'Tailored to your exact specifications.', 'settings', 0.00, false, 12, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Seed portfolio categories
INSERT INTO portfolio_categories (name, slug, description) VALUES
('Branding', 'branding', 'Logo and brand identity projects'),
('Print Design', 'print-design', 'Print collateral and marketing materials'),
('Packaging', 'packaging', 'Product packaging designs'),
('Digital', 'digital', 'Digital and social media designs');

-- Seed portfolio items
INSERT INTO portfolios (title, slug, description, image_url, thumbnail_url, client, featured, category_id) VALUES
('Luxury Brand Identity', 'luxury-brand-identity', 'Complete brand identity for a premium fashion label.', 'https://images.unsplash.com/photo-1626785774573-4b799314346d?w=800', 'https://images.unsplash.com/photo-1626785774573-4b799314346d?w=400', 'Vogue India', true, 1),
('Restaurant Menu Design', 'restaurant-menu-design', 'Elegant menu design for a fine dining restaurant.', 'https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=800', 'https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=400', 'Spice Garden', true, 2),
('Product Packaging Suite', 'product-packaging-suite', 'Complete packaging design for organic skincare line.', 'https://images.unsplash.com/photo-1556228723-22a7cfe4472d?w=800', 'https://images.unsplash.com/photo-1556228723-22a7cfe4472d?w=400', 'NatureGlow', true, 3),
('Social Media Campaign', 'social-media-campaign', '30-day social media design campaign for tech startup.', 'https://images.unsplash.com/photo-1611162617474-5b21e879e113?w=800', 'https://images.unsplash.com/photo-1611162617474-5b21e879e113?w=400', 'TechFlow', true, 4),
('Wedding Invitation Suite', 'wedding-invitation-suite', 'Royal wedding card design with gold foil accents.', 'https://images.unsplash.com/photo-1519741497674-611481863552?w=800', 'https://images.unsplash.com/photo-1519741497674-611481863552?w=400', 'Sharma Family', true, 2),
('Corporate Brochure', 'corporate-brochure', 'Annual report and corporate brochure design.', 'https://images.unsplash.com/photo-1586281380349-a63205f9f987?w=800', 'https://images.unsplash.com/photo-1586281380349-a63205f9f987?w=400', 'Infosys Partner', false, 2);

-- Seed testimonials
INSERT INTO testimonials (client_name, company, designation, content, rating, featured) VALUES
('Rajesh Kumar', 'Kumar Enterprises', 'CEO', 'Shivam Printers transformed our brand identity completely. Their attention to detail and premium quality is unmatched in the industry.', 5, true),
('Priya Sharma', 'Sharma Events', 'Founder', 'Our wedding cards were absolutely stunning. Every guest complimented the design. Highly recommend their services!', 5, true),
('Amit Patel', 'Patel Foods', 'Marketing Head', 'Fast turnaround, exceptional quality, and competitive pricing. They are our go-to partner for all printing needs.', 5, true),
('Neha Gupta', 'Gupta Boutique', 'Owner', 'The packaging design increased our product sales by 40%. Professional team with creative excellence.', 5, false);

-- Seed blog posts
INSERT INTO blog_posts (id, title, slug, excerpt, content, author, tags, published, published_at) VALUES
('a1b2c3d4-e5f6-7890-abcd-ef1234567890', '10 Design Trends Shaping Print in 2026', 'design-trends-2026', 'Discover the latest trends in print and graphic design.', '<p>The printing industry continues to evolve with sustainable materials, bold typography, and minimalist aesthetics leading the way.</p>', 'Shivam Printers Team', 'design,trends,2026', true, NOW()),
('b2c3d4e5-f6a7-8901-bcde-f12345678901', 'How to Choose the Perfect Business Card', 'choose-business-card', 'A guide to selecting the right business card design and finish.', '<p>Your business card is often the first impression. Learn about paper stocks, finishes, and design principles.</p>', 'Shivam Printers Team', 'business-cards,tips', true, NOW()),
('c3d4e5f6-a7b8-9012-cdef-123456789012', 'The Power of Brand Consistency', 'brand-consistency', 'Why consistent branding matters for your business growth.', '<p>Consistent branding across all touchpoints builds trust and recognition. Here is how to achieve it.</p>', 'Shivam Printers Team', 'branding,marketing', true, NOW());
